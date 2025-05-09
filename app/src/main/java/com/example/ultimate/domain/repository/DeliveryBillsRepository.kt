package com.example.ultimate.domain.repository

import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import com.example.ultimate.utils.Constants

interface DeliveryBillsRepository {

    suspend fun getStatusTypes(
        languageNo: String = Constants.LANG_AR
    ): StatusTypesResponse

    suspend fun getDeliveryBills(
        deliveryNo: String,
        languageNo: String = Constants.LANG_AR,
        billSerial: String = "",
        processedFlag: String = ""
    ): DeliveryBillsResponse
}