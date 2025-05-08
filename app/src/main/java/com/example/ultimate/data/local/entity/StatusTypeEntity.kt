package com.example.ultimate.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// StatusTypeEntity.kt
@Entity(tableName = "status_types")
data class StatusTypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "type_number")
    val typeNumber: String,
    @ColumnInfo(name = "type_name")
    val typeName: String,
    @ColumnInfo(name = "language_no")
    val languageNo: String,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)