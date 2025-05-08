package com.example.ultimate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ultimate.data.local.entity.DeliveryBillEntity


// DeliveryBillDao.kt
@Dao
interface DeliveryBillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBills(bills: List<DeliveryBillEntity>)

    @Query("SELECT * FROM delivery_bills WHERE language_no = :languageNo")
    suspend fun getBills(languageNo: String): List<DeliveryBillEntity>

    @Query("DELETE FROM delivery_bills WHERE language_no = :languageNo")
    suspend fun clearBills(languageNo: String)
}