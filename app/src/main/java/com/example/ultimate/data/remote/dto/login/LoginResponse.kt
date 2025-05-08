package com.example.ultimate.data.remote.dto.login

import com.example.ultimate.data.remote.dto.OperationResult
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Data")
    val data: LoginData,
    @SerializedName("Result")
    val result: OperationResult
)

data class LoginData(
    @SerializedName("DeliveryName")
    val deliveryName: String  // "احمد عبدالقوي عبدالله حسان"
)
