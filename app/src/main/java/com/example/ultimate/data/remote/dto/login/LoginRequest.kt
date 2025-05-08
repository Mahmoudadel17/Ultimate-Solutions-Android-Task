package com.example.ultimate.data.remote.dto.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("Value")
    val value: LoginCredentials
)

data class LoginCredentials(
    @SerializedName("P_LANG_NO")
    val languageNo: String,  // "2" for English
    @SerializedName("P_DLVRY_NO")
    val deliveryNo: String,  // "1010"
    @SerializedName("P_PSSWRD")
    val password: String     // "1"
)