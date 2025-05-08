package com.example.ultimate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ultimate.data.local.dao.DeliveryBillDao
import com.example.ultimate.data.local.dao.StatusTypeDao
import com.example.ultimate.data.local.entity.DeliveryBillEntity
import com.example.ultimate.data.local.entity.StatusTypeEntity

// AppDatabase.kt
@Database(
    entities = [StatusTypeEntity::class, DeliveryBillEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statusTypeDao(): StatusTypeDao
    abstract fun deliveryBillDao(): DeliveryBillDao


    //
//    companion object {
//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "ultimate_db"
//                ).build().also { instance = it }
//            }
//        }
//    }
}