package com.example.thecatapp.cat.repo

import com.example.thecatapp.cat.models.Breed
import com.example.thecatapp.network.Result

interface CatRepo {
    suspend fun getCatImagesList(page: Int): Result<List<Breed>>
}