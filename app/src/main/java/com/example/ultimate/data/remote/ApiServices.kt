package com.example.ultimate.data.remote

import com.example.ultimate.data.remote.dto.login.LoginRequest
import com.example.ultimate.data.remote.dto.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("CheckDeliveryLogin")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse



}