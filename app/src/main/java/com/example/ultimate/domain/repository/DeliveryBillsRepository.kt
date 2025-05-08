package com.example.ultimate.domain.repository

import com.example.ultimate.data.remote.dto.status.StatusTypesResponse

interface DeliveryBillsRepository {

    suspend fun getStatusTypes(languageNo: String = "1"): StatusTypesResponse


}