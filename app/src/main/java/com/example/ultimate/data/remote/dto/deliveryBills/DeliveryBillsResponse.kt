package com.example.ultimate.data.remote.dto.deliveryBills

import com.example.ultimate.data.remote.dto.OperationResult
import com.google.gson.annotations.SerializedName

data class DeliveryBillsResponse(
    @SerializedName("Data")
    val data: DeliveryBillsData,
    @SerializedName("Result")
    val result: OperationResult // Reusing from previous models
)

data class DeliveryBillsData(
    @SerializedName("DeliveryBills")
    val bills: List<DeliveryBill>
)

data class DeliveryBill(
    @SerializedName("BILL_TYPE")
    val billType: String,
    @SerializedName("BILL_NO")
    val billNumber: String,
    @SerializedName("BILL_SRL")
    val billSerial: String,
    @SerializedName("BILL_DATE")
    val billDate: String,
    @SerializedName("BILL_TIME")
    val billTime: String,
    @SerializedName("BILL_AMT")
    val billAmount: String,
    @SerializedName("TAX_AMT")
    val taxAmount: String,
    @SerializedName("DLVRY_AMT")
    val deliveryAmount: String,
    @SerializedName("MOBILE_NO")
    val mobileNumber: String,
    @SerializedName("CSTMR_NM")
    val customerName: String,
    @SerializedName("RGN_NM")
    val regionName: String,
    @SerializedName("CSTMR_BUILD_NO")
    val buildingNumber: String,
    @SerializedName("CSTMR_FLOOR_NO")
    val floorNumber: String,
    @SerializedName("CSTMR_APRTMNT_NO")
    val apartmentNumber: String,
    @SerializedName("CSTMR_ADDRSS")
    val customerAddress: String,
    @SerializedName("LATITUDE")
    val latitude: String,
    @SerializedName("LONGITUDE")
    val longitude: String,
    @SerializedName("DLVRY_STATUS_FLG")
    val deliveryStatusFlag: String
) {
    // Helper computed properties
    val formattedAddress: String
        get() = listOf(
            customerAddress,
            buildingNumber.takeIf { it.isNotEmpty() }?.let { "Building $it" },
            floorNumber.takeIf { it.isNotEmpty() }?.let { "Floor $it" },
            apartmentNumber.takeIf { it.isNotEmpty() }?.let { "Apartment $it" },
            regionName.takeIf { it.isNotEmpty() }
        ).filterNotNull().joinToString(", ")

    val totalAmount: Double
        get() = (billAmount.toDoubleOrNull() ?: 0.0) +
                (taxAmount.toDoubleOrNull() ?: 0.0) +
                (deliveryAmount.toDoubleOrNull() ?: 0.0)
}