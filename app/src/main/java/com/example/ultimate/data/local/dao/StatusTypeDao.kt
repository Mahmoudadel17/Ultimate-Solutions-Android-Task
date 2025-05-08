package com.example.ultimate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ultimate.data.local.entity.StatusTypeEntity

// StatusTypeDao.kt
@Dao
interface StatusTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatusTypes(statusTypes: List<StatusTypeEntity>)

    @Query("SELECT * FROM status_types WHERE language_no = :languageNo")
    suspend fun getStatusTypes(languageNo: String): List<StatusTypeEntity>

    @Query("DELETE FROM status_types WHERE language_no = :languageNo")
    suspend fun clearStatusTypes(languageNo: String)
}