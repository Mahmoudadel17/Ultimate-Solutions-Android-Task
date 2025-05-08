package com.example.ultimate.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OperationResult(
    @SerializedName("ErrNo")
    val errorNumber: Int,     // 0 for success
    @SerializedName("ErrMsg")
    val errorMessage: String  // "Successful"
)