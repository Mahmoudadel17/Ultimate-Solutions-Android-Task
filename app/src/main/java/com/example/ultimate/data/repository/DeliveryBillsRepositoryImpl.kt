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

class DeliveryBillsRepositoryImpl  @Inject constructor(
    private val api: ApiServices,
    private val deliveryBillDao: DeliveryBillDao,
    private val statuesTypeDao: StatusTypeDao


) : DeliveryBillsRepository {

    override suspend fun getStatusTypes(languageNo: String): StatusTypesResponse {
        return try {
            val apiResponse = api.getStatusTypes(
                StatusTypesRequest(
                    StatusTypesQuery(languageNo = languageNo)
                )
            )

            if (apiResponse.result?.errorNumber == 0) {
                // API success - save to DB
                apiResponse.data?.statusTypes?.let { types ->
                    val entities = types.map { type ->
                        StatusTypeEntity(
                            typeNumber = type.typeNumber ?: "",
                            typeName = type.typeName ?: "",
                            languageNo = languageNo
                        )
                    }
                    statuesTypeDao.clearStatusTypes(languageNo)
                    statuesTypeDao.insertStatusTypes(entities)
                }
                apiResponse
            } else {
                // API error - fallback to DB
                val cachedTypes = statuesTypeDao.getStatusTypes(languageNo)
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
            // Network failure - fallback to DB
            val cachedTypes = statuesTypeDao.getStatusTypes(languageNo)
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
        return try {
            // First try to get from API
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

            if (apiResponse.result?.errorNumber == 0) {
                // API success - save to DB
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
                    deliveryBillDao.clearBills(languageNo)
                    deliveryBillDao.insertBills(entities)
                }
                apiResponse
            } else {
                // API returned error - fallback to DB
                val cachedBills = deliveryBillDao.getBills(languageNo)
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
                        errorNumber = 0, // Custom code for cached data, but we will make it as 0 now to can display it on UI.
                        errorMessage = "Falling back to cached data: ${apiResponse.result?.errorMessage}"
                    )
                )
            }
        } catch (e: Exception) {
            // Network failure - fallback to DB
            val cachedBills = deliveryBillDao.getBills(languageNo)
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
                    errorNumber = 0, // Custom code for network failure, but we will make it as 0 now to can display it on UI.
                    errorMessage = "Network error, falling back to cached data: ${e.message}"
                )
            )
        }
    }


}