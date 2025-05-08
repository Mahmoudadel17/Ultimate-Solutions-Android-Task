package com.example.ultimate.data.repository

import com.example.ultimate.data.local.dao.DeliveryBillDao
import com.example.ultimate.data.local.dao.StatusTypeDao
import com.example.ultimate.data.local.entity.DeliveryBillEntity
import com.example.ultimate.data.local.entity.StatusTypeEntity
import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.remote.dto.OperationResult
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsData
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsQuery
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsRequest
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
import com.example.ultimate.data.remote.dto.status.DeliveryStatusType
import com.example.ultimate.data.remote.dto.status.StatusTypesData
import com.example.ultimate.data.remote.dto.status.StatusTypesQuery
import com.example.ultimate.data.remote.dto.status.StatusTypesRequest
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import javax.inject.Inject
import android.util.Log

class DeliveryBillsRepositoryImpl @Inject constructor(
    private val api: ApiServices,
    private val deliveryBillDao: DeliveryBillDao,
    private val statuesTypeDao: StatusTypeDao
) : DeliveryBillsRepository {

    private companion object {
        const val TAG = "DeliveryRepo"
    }

    override suspend fun getStatusTypes(languageNo: String): StatusTypesResponse {
        Log.d(TAG, "getStatusTypes() called with languageNo: $languageNo")

        return try {
            Log.d(TAG, "Attempting to fetch status types from API")
            val apiResponse = api.getStatusTypes(
                StatusTypesRequest(
                    StatusTypesQuery(languageNo = languageNo)
                )
            )

            Log.d(TAG, "API response received: ${apiResponse.result?.errorNumber} - ${apiResponse.result?.errorMessage}")

            if (apiResponse.result?.errorNumber == 0) {
                Log.d(TAG, "API success - processing status types")
                apiResponse.data?.statusTypes?.let { types ->
                    val entities = types.map { type ->
                        StatusTypeEntity(
                            typeNumber = type.typeNumber ?: "",
                            typeName = type.typeName ?: "",
                            languageNo = languageNo
                        )
                    }
                    Log.d(TAG, "Clearing old status types for language: $languageNo")
                    statuesTypeDao.clearStatusTypes(languageNo)

                    Log.d(TAG, "Inserting ${entities.size} status types into DB")
                    statuesTypeDao.insertStatusTypes(entities)
                }
                apiResponse
            } else {
                Log.w(TAG, "API error - falling back to DB cache. Error: ${apiResponse.result?.errorMessage}")
                val cachedTypes = statuesTypeDao.getStatusTypes(languageNo)
                Log.d(TAG, "Found ${cachedTypes.size} cached status types")

                StatusTypesResponse(
                    StatusTypesData(
                        statusTypes = cachedTypes.map {
                            DeliveryStatusType(
                                typeNumber = it.typeNumber,
                                typeName = it.typeName
                            )
                        }
                    ),
                    OperationResult(
                        errorNumber = 0,
                        errorMessage = "Falling back to cached data: ${apiResponse.result?.errorMessage}"
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network failure - falling back to DB cache", e)
            val cachedTypes = statuesTypeDao.getStatusTypes(languageNo)
            Log.d(TAG, "Found ${cachedTypes.size} cached status types")

            StatusTypesResponse(
                StatusTypesData(
                    statusTypes = cachedTypes.map {
                        DeliveryStatusType(
                            typeNumber = it.typeNumber,
                            typeName = it.typeName
                        )
                    }
                ),
                OperationResult(
                    errorNumber = 0,
                    errorMessage = "Network error, falling back to cached data: ${e.message}"
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
        Log.d(TAG, "getDeliveryBills() called with deliveryNo: $deliveryNo, languageNo: $languageNo")

        return try {
            Log.d(TAG, "Attempting to fetch delivery bills from API")
            val apiResponse = api.getDeliveryBills(
                DeliveryBillsRequest(
                    DeliveryBillsQuery(
                        deliveryNo = deliveryNo,
                        languageNo = languageNo,
                        billSerial = billSerial,
                        processedFlag = processedFlag
                    )
                )
            )

            Log.d(TAG, "API response received: ${apiResponse.result?.errorNumber} - ${apiResponse.result?.errorMessage}")

            if (apiResponse.result?.errorNumber == 0) {
                Log.d(TAG, "API success - processing delivery bills")
                apiResponse.data?.bills?.let { bills ->
                    val entities = bills.map { bill ->
                        DeliveryBillEntity(
                            billSerial = bill.billSerial ?: "",
                            languageNo = languageNo,
                            billType = bill.billType ?: "",
                            billNumber = bill.billNumber ?: "",
                            billDate = bill.billDate ?: "",
                            billTime = bill.billTime ?: "",
                            billAmount = bill.billAmount ?: "",
                            taxAmount = bill.taxAmount ?: "",
                            deliveryAmount = bill.deliveryAmount ?: "",
                            mobileNumber = bill.mobileNumber ?: "",
                            customerName = bill.customerName ?: "",
                            regionName = bill.regionName ?: "",
                            buildingNumber = bill.buildingNumber ?: "",
                            floorNumber = bill.floorNumber ?: "",
                            apartmentNumber = bill.apartmentNumber ?: "",
                            customerAddress = bill.customerAddress ?: "",
                            latitude = bill.latitude ?: "",
                            longitude = bill.longitude ?: "",
                            deliveryStatusFlag = bill.deliveryStatusFlag ?: ""
                        )
                    }
                    Log.d(TAG, "Clearing old bills for language: $languageNo")
                    deliveryBillDao.clearBills(languageNo)

                    Log.d(TAG, "Inserting ${entities.size} bills into DB")
                    deliveryBillDao.insertBills(entities)
                }
                apiResponse
            } else {
                Log.w(TAG, "API error - falling back to DB cache. Error: ${apiResponse.result?.errorMessage}")
                val cachedBills = deliveryBillDao.getBills(languageNo)
                Log.d(TAG, "Found ${cachedBills.size} cached bills")

                DeliveryBillsResponse(
                    DeliveryBillsData(
                        bills = cachedBills.map {
                            DeliveryBill(
                                billSerial = it.billSerial,
                                billType = it.billType,
                                billNumber = it.billNumber,
                                billDate = it.billDate,
                                billTime = it.billTime,
                                billAmount = it.billAmount,
                                taxAmount = it.taxAmount,
                                deliveryAmount = it.deliveryAmount,
                                mobileNumber = it.mobileNumber,
                                customerName = it.customerName,
                                regionName = it.regionName,
                                buildingNumber = it.buildingNumber,
                                floorNumber = it.floorNumber,
                                apartmentNumber = it.apartmentNumber,
                                customerAddress = it.customerAddress,
                                latitude = it.latitude,
                                longitude = it.longitude,
                                deliveryStatusFlag = it.deliveryStatusFlag
                            )
                        }
                    ),
                    OperationResult(
                        errorNumber = 0,
                        errorMessage = "Falling back to cached data: ${apiResponse.result?.errorMessage}"
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network failure - falling back to DB cache", e)
            val cachedBills = deliveryBillDao.getBills(languageNo)
            Log.d(TAG, "Found ${cachedBills.size} cached bills")

            DeliveryBillsResponse(
                DeliveryBillsData(
                    bills = cachedBills.map {
                        DeliveryBill(
                            billSerial = it.billSerial,
                            billType = it.billType,
                            billNumber = it.billNumber,
                            billDate = it.billDate,
                            billTime = it.billTime,
                            billAmount = it.billAmount,
                            taxAmount = it.taxAmount,
                            deliveryAmount = it.deliveryAmount,
                            mobileNumber = it.mobileNumber,
                            customerName = it.customerName,
                            regionName = it.regionName,
                            buildingNumber = it.buildingNumber,
                            floorNumber = it.floorNumber,
                            apartmentNumber = it.apartmentNumber,
                            customerAddress = it.customerAddress,
                            latitude = it.latitude,
                            longitude = it.longitude,
                            deliveryStatusFlag = it.deliveryStatusFlag
                        )
                    }
                ),
                OperationResult(
                    errorNumber = 0,
                    errorMessage = "Network error, falling back to cached data: ${e.message}"
                )
            )
        }
    }
}