package com.example.ultimate.data.repository

import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.remote.dto.OperationResult
import com.example.ultimate.data.remote.dto.status.StatusTypesData
import com.example.ultimate.data.remote.dto.status.StatusTypesQuery
import com.example.ultimate.data.remote.dto.status.StatusTypesRequest
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import javax.inject.Inject

class DeliveryBillsRepositoryImpl  @Inject constructor(
    private val api: ApiServices
) : DeliveryBillsRepository {


    override suspend fun getStatusTypes(languageNo: String ): StatusTypesResponse {
        return try {
            val response = api.getStatusTypes(
                StatusTypesRequest(
                    StatusTypesQuery(languageNo = languageNo)
                )
            )
            response
        } catch (e: Exception) {
            StatusTypesResponse(
                StatusTypesData(
                    statusTypes = emptyList()
                ),
                OperationResult(
                    errorNumber = -1,
                    errorMessage = e.message ?: "Network error"
                )
            )
        }
    }




}