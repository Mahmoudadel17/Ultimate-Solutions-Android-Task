package com.example.ultimate.data.repository

import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.remote.dto.OperationResult
import com.example.ultimate.data.remote.dto.login.LoginCredentials
import com.example.ultimate.data.remote.dto.login.LoginData
import com.example.ultimate.data.remote.dto.login.LoginRequest
import com.example.ultimate.data.remote.dto.login.LoginResponse
import com.example.ultimate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiServices
) : AuthRepository {


    override suspend fun login(
        deliveryNo: String,
        password: String,
        languageNo: String
    ): LoginResponse {
        return try {
            val response = api.login(
                LoginRequest(
                    LoginCredentials(
                        languageNo = languageNo,
                        deliveryNo = deliveryNo,
                        password = password
                    )
                )
            )
            response
        } catch (e: Exception) {
          LoginResponse(
              LoginData(
                  deliveryName = ""
              ),
              OperationResult(
                  errorNumber = -1,
                  errorMessage = e.message ?: "Network error"
              )
          )
        }
    }
}