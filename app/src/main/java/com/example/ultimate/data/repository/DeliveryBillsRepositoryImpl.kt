package com.example.ultimate.data.repository

import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.remote.dto.OperationResult
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsData
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsQuery
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsRequest
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
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

    override suspend fun getDeliveryBills(
        deliveryNo: String,
        languageNo: String,
        billSerial: String,
        processedFlag: String
    ): DeliveryBillsResponse {
        return try {
            val response = api.getDeliveryBills(
                DeliveryBillsRequest(
                    DeliveryBillsQuery(
                        deliveryNo = deliveryNo,
                        languageNo = languageNo,
                        billSerial = billSerial,
                        processedFlag = processedFlag
                    )
                )
            )

            response

        } catch (e: Exception) {
            DeliveryBillsResponse(
                DeliveryBillsData(
                    bills = emptyList()
                ),
                OperationResult(
                    errorNumber = -1,
                    errorMessage = e.message ?: "Network error"
                )
            )
        }
    }


}