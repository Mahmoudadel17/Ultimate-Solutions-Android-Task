package com.example.ultimate.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


// DeliveryBillEntity.kt
@Entity(tableName = "delivery_bills")
data class DeliveryBillEntity(
    @PrimaryKey
    @ColumnInfo(name = "bill_serial")
    val billSerial: String,
    @ColumnInfo(name = "language_no")
    val languageNo: String,
    @ColumnInfo(name = "bill_type")
    val billType: String,
    @ColumnInfo(name = "bill_number")
    val billNumber: String,
    @ColumnInfo(name = "bill_date")
    val billDate: String,
    @ColumnInfo(name = "bill_time")
    val billTime: String,
    @ColumnInfo(name = "bill_amount")
    val billAmount: String,
    @ColumnInfo(name = "tax_amount")
    val taxAmount: String,
    @ColumnInfo(name = "delivery_amount")
    val deliveryAmount: String,
    @ColumnInfo(name = "mobile_number")
    val mobileNumber: String,
    @ColumnInfo(name = "customer_name")
    val customerName: String,
    @ColumnInfo(name = "region_name")
    val regionName: String,
    @ColumnInfo(name = "buildingNumber")
    val buildingNumber: String,
    @ColumnInfo(name = "floorNumber")
    val floorNumber: String,
    @ColumnInfo(name = "apartmentNumber")
    val apartmentNumber: String,
    @ColumnInfo(name = "customerAddress")
    val customerAddress: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "deliveryStatusFlag")
    val deliveryStatusFlag: String,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)

