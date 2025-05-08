package com.example.ultimate.data.remote.dto.deliveryBills

import com.google.gson.annotations.SerializedName

data class DeliveryBillsRequest(
    @SerializedName("Value")
    val value: DeliveryBillsQuery
)

data class DeliveryBillsQuery(
    @SerializedName("P_DLVRY_NO")
    val deliveryNo: String,      // "1010"
    @SerializedName("P_LANG_NO")
    val languageNo: String,     // "1" for Arabic
    @SerializedName("P_BILL_SRL")
    val billSerial: String = "", // Optional filter
    @SerializedName("P_PRCSSD_FLG")
    val processedFlag: String = "" // Optional filter
)