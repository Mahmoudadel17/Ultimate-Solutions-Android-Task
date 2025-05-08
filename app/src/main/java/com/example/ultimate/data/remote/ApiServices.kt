package com.example.ultimate.data.remote

import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsRequest
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBillsResponse
import com.example.ultimate.data.remote.dto.login.LoginRequest
import com.example.ultimate.data.remote.dto.login.LoginResponse
import com.example.ultimate.data.remote.dto.status.StatusTypesRequest
import com.example.ultimate.data.remote.dto.status.StatusTypesResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("CheckDeliveryLogin")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse


    @POST("GetDeliveryStatusTypes")
    suspend fun getStatusTypes(
        @Body request: StatusTypesRequest
    ): StatusTypesResponse


    @POST("GetDeliveryBillsItems")
    suspend fun getDeliveryBills(
        @Body request: DeliveryBillsRequest
    ): DeliveryBillsResponse

}