package com.example.network.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CatRetrofitService {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCatApiService(retrofit: Retrofit): com.example.network.api.CatApi {
        return retrofit.create(com.example.network.api.CatApi::class.java)
    }
}

const val BASE_URL = "https://api.thecatapi.com/"