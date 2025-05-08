package com.example.ultimate.di

import android.app.Application
import com.example.ultimate.data.remote.ApiServices
import com.example.ultimate.data.repository.AuthRepositoryImpl
import com.example.ultimate.data.repository.DeliveryBillsRepositoryImpl
import com.example.ultimate.domain.repository.AuthRepository
import com.example.ultimate.domain.repository.DeliveryBillsRepository
import com.example.ultimate.utils.Constants
import com.example.ultimate.utils.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
    fun providesGeneralApiServices () : ApiServices = providesGeneralRetrofit().create(ApiServices::class.java)


    // Auth repository
    @Provides
    @Singleton
    fun providesAuthApiRepository(): AuthRepository = AuthRepositoryImpl(providesGeneralApiServices())



    // Delivery Bills Repository
    @Provides
    @Singleton
    fun providesGeneralApiRepository(): DeliveryBillsRepository = DeliveryBillsRepositoryImpl(providesGeneralApiServices())



    @Singleton
    @Provides
    fun provideSharedPreferencesInstance(application: Application): SharedPreferences {
        return SharedPreferences(application)
    }

}