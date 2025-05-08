package com.example.ultimate.presentation.home


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill
import com.example.ultimate.data.remote.dto.deliveryBills.isToday
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class  HomeViewModel  @Inject constructor(
    private  val repo:DeliveryBillsRepository,
    private val pref: SharedPreferences
): ViewModel() {

    private val _statusTypes = mutableStateOf<StatusTypesResponse?>(null)
    val statusTypes: State<StatusTypesResponse?> = _statusTypes

    private val _billsState = mutableStateOf<BillsState>(BillsState.Loading)
    val billsState: State<BillsState> = _billsState

    private val _selectedTab = mutableIntStateOf(0) // 0 for New, 1 for Others
    val selectedTab: State<Int> = _selectedTab

//    init {
//        val language = pref.getSharedPreferences(Constants.LANG,"1")
//        loadStatusTypes(language)
//        loadBills(
//            deliveryNo = getDeliveryName(),
//            languageNo =  pref.getSharedPreferences(Constants.LANG,"")
//        )
//    }
init {
    refresh()
}


    fun refresh(){
        val language = pref.getSharedPreferences(Constants.LANG,"1")
        loadStatusTypes(language)
        loadBills(
            deliveryNo = pref.getSharedPreferences(Constants.DELIVERY_NO,""),
            languageNo =  pref.getSharedPreferences(Constants.LANG,"")
        )
    }


    fun selectTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }


    fun getDeliveryName():String{
        return  pref.getSharedPreferences(Constants.DELIVERY_NAME,"")
    }

    private fun loadStatusTypes(languageNo: String = "1") {
        viewModelScope.launch {
            _statusTypes.value = repo.getStatusTypes(languageNo)
        }
    }



    private fun loadBills(
        deliveryNo: String,
        languageNo: String = "1",
        billSerial: String = "",
        processedFlag: String = ""
    ) {
        viewModelScope.launch {
            _billsState.value = BillsState.Loading
            try {
                val response = repo.getDeliveryBills(
                    deliveryNo,
                    languageNo,
                    billSerial,
                    processedFlag
                )
                _billsState.value = if (response.result.errorNumber == 0 ) {
                    BillsState.Success(response.data.bills)
                } else {
                    BillsState.Empty
                }
            } catch (e: Exception) {
                _billsState.value = BillsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getStatusName(typeNumber: String): String? {
        return (_statusTypes.value )?.data?.statusTypes
            ?.firstOrNull { it.typeNumber == typeNumber }
            ?.typeName

    }


    fun getFilteredBills(): List<DeliveryBill> {
        return when (val state = billsState.value) {
            is BillsState.Success -> {
                if (selectedTab.value == 0) {
                    state.bills.filter { it.isToday() }
                } else {
                    state.bills.filterNot { it.isToday() }
                }
            }
            else -> emptyList()
        }
    }
}