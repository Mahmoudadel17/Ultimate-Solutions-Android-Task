package com.example.ultimate.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimate.domain.repository.AuthRepository
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private  val repo:AuthRepository,
    private val pref: SharedPreferences
): ViewModel() {
    private var _state by mutableStateOf(LoginScreenState())

    val state: State<LoginScreenState>
               get() = derivedStateOf { _state }


    fun onUserIdChange(userId: String) {
        _state = _state.copy(userId = userId)
    }

    fun onPasswordChange(password: String) {
        _state = _state.copy(password = password)
    }



    fun login(onSuccess: () -> Unit) {

        viewModelScope.launch {
            //
            _state = _state.copy(isLoading = true, error = null)
            //
            val language = pref.getSharedPreferences(Constants.LANG,"1")

            try {

               val response =  repo.login(
                    deliveryNo = _state.userId,
                    password = _state.password,
                    languageNo = language
                )


                if (response.result.errorNumber != 0){
                    _state = _state.copy(
                        error =  response.result.errorMessage,
                        isLoading = false
                    )
                }else{
                    pref.setSharedPreferences(Constants.DELIVERY_NO,_state.userId)
                    pref.setSharedPreferences(Constants.DELIVERY_NAME,response.data.deliveryName)

                    // must be create toggle for language TODO
                    pref.setSharedPreferences(Constants.LANG,"1")

                    _state = _state.copy(
                        isLoading = false
                    )
                    // success, navigate into home screen after store the token, (session)
                    onSuccess()
                }

            } catch (e: Exception) {
                _state = _state.copy(
                    error = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
}