package com.example.ultimate.presentation.auth

data class LoginScreenState(
    val userId: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isShowPassword: Boolean = false,
    val error: String? = null
)