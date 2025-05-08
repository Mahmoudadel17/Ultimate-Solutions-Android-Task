package com.example.ultimate.presentation.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ultimate.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private  val repo:AuthRepository
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
            _state = _state.copy(isLoading = true, error = null)
            try {

                delay(1000)
                if (_state.userId.isEmpty() || _state.password.isEmpty()){
                    _state = _state.copy(
                        error =  "Login failed",
                        isLoading = false
                    )
                }else{
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