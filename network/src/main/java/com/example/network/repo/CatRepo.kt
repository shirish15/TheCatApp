package com.example.network.repo

import com.example.network.models.Breed
import com.example.network.network.Result

interface CatRepo {
    suspend fun getCatImagesList(page: Int): Result<List<Breed>>
}