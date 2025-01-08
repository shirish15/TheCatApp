package com.example.network.api

import com.example.network.models.Breed
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("v1/breeds")
    suspend fun getCatImagesList(
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): List<Breed>
}