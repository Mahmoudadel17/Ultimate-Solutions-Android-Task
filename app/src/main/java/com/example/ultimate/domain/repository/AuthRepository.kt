package com.example.ultimate.domain.repository

import com.example.ultimate.data.remote.dto.login.LoginResponse

interface AuthRepository {
    suspend fun login(
        deliveryNo: String,
        password: String,
        languageNo: String = "2"
    ): LoginResponse
}