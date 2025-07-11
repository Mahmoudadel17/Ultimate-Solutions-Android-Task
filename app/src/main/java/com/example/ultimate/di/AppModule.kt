package com.example.ultimate.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ultimate.data.local.AppDatabase
import com.example.ultimate.data.local.dao.DeliveryBillDao
import com.example.ultimate.data.local.dao.StatusTypeDao
import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.repository.AuthRepositoryImpl
import com.example.ultimate.data.repository.DeliveryBillsRepositoryImpl
import com.example.ultimate.domain.repository.AuthRepository
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SessionManager
import com.example.ultimate.utils.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Database
    @Provides
    @Singleton
    fun providesDatabase (@ApplicationContext context: Context) =
        Room.databaseBuilder(context,AppDatabase::class.java,"ultimate_db").build()

    @Provides
    @Singleton
    fun providesDatabaseStatusTypeDao (database: AppDatabase) = database.statusTypeDao()


    @Provides
    @Singleton
    fun providesDatabaseDeliveryBillDao (database: AppDatabase) = database.deliveryBillDao()


    //  Api services
    @Provides
    @Singleton
    fun providesGeneralRetrofit (): Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.API_SERVICES_URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun providesGeneralApiServices(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    // Auth repository
    @Provides
    @Singleton
    fun providesAuthApiRepository(apiServices: ApiServices): AuthRepository =
        AuthRepositoryImpl(apiServices)

    // Delivery Bills Repository
    @Provides
    @Singleton
    fun providesGeneralApiRepository(
        apiServices: ApiServices,
        deliveryBillDao: DeliveryBillDao,
        statusTypeDao: StatusTypeDao
    ): DeliveryBillsRepository =
        DeliveryBillsRepositoryImpl(apiServices, deliveryBillDao, statusTypeDao)


    @Singleton
    @Provides
    fun provideSharedPreferencesInstance(application: Application): SharedPreferences {
        return SharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideSessionManager(
        pref: SharedPreferences
    ): SessionManager {
        return SessionManager(pref)
    }


}