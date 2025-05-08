package com.example.ultimate.data.remote.dto.status

import com.example.ultimate.data.remote.dto.OperationResult
import com.google.gson.annotations.SerializedName

data class StatusTypesResponse(
    @SerializedName("Data")
    val data: StatusTypesData,
    @SerializedName("Result")
    val result: OperationResult // Reusing the same OperationResult from login
)

data class StatusTypesData(
    @SerializedName("DeliveryStatusTypes")
    val statusTypes: List<DeliveryStatusType>
)

data class DeliveryStatusType(
    @SerializedName("TYP_NO")
    val typeNumber: String,
    @SerializedName("TYP_NM")
    val typeName: String
)