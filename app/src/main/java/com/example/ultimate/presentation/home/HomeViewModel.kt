package com.example.ultimate.presentation.home


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
import com.example.ultimate.data.remote.dto.deliveryBills.getStatusColor
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject


class  HomeViewModel  @Inject constructor(
    private  val repo:DeliveryBillsRepository,
    private val pref: SharedPreferences
): ViewModel() {

    private val _statusTypes = mutableStateOf<StatusTypesResponse?>(null)
    val statusTypes: State<StatusTypesResponse?> = _statusTypes


    private val _billsState = mutableStateOf<DeliveryBillsResponse?>(null)
    val billsState: State<DeliveryBillsResponse?> = _billsState


    init {
        val language = pref.getSharedPreferences(Constants.LANG,"1")
        loadStatusTypes(language)
        loadBills(
            deliveryNo = getDeliveryName(),
            languageNo =  pref.getSharedPreferences(Constants.LANG,"")

        )

    }

    fun getDeliveryName():String{
        return  pref.getSharedPreferences(Constants.DELIVERY_NAME,"")
    }

    private fun loadStatusTypes(languageNo: String = "1") {
        viewModelScope.launch {
            _statusTypes.value = repo.getStatusTypes(languageNo)
        }
    }

    fun getStatusName(typeNumber: String): String? {
        return (_statusTypes.value )?.data?.statusTypes
            ?.firstOrNull { it.typeNumber == typeNumber }
            ?.typeName

    }



    private fun loadBills(
        deliveryNo: String,
        languageNo: String = "1",
        billSerial: String = "",
        processedFlag: String = ""
    ) {
        viewModelScope.launch {
            _billsState.value = repo.getDeliveryBills(
                deliveryNo,
                languageNo,
                billSerial,
                processedFlag
            )
        }
    }

    fun filterBillsByStatus(statusFlag: String): List<DeliveryBill> {
        return (_billsState.value )?.data?.bills
            ?.filter { it.deliveryStatusFlag == statusFlag }
            ?: emptyList()
    }

}