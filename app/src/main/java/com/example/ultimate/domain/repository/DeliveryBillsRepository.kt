package com.example.ultimate.domain.repository

import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse

interface DeliveryBillsRepository {

    suspend fun getStatusTypes(
        languageNo: String = "1"
    ): StatusTypesResponse

    suspend fun getDeliveryBills(
        deliveryNo: String,
        languageNo: String = "1",
        billSerial: String = "",
        processedFlag: String = ""
    ): DeliveryBillsResponse
}