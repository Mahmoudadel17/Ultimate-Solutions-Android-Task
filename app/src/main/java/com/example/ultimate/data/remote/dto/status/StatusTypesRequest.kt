package com.example.ultimate.data.remote.dto.status

import com.google.gson.annotations.SerializedName

data class StatusTypesRequest(
    @SerializedName("Value")
    val value: StatusTypesQuery
)

data class StatusTypesQuery(
    @SerializedName("P_LANG_NO")
    val languageNo: String  // "1" for Arabic
)