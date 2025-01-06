package com.example.thecatapp.cat.repo.impl

import com.example.thecatapp.cat.models.Breed
import com.example.thecatapp.cat.api.CatApi
import com.example.thecatapp.network.Result
import com.example.thecatapp.cat.repo.CatRepo
import javax.inject.Inject


class CatRepoImpl @Inject constructor(private val catApiService: CatApi) : CatRepo {

    override suspend fun getCatImagesList(page: Int): Result<List<Breed>> {
        return try {
            Result.Success(
                catApiService.getCatImagesList(
                    apiKey = API_KEY,
                    limit = PAGE_SIZE,
                    page = page,
                )
            )
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    companion object {
        const val API_KEY = "live_RCXKe1T1M00ak5kZFpniNBD5ivVaEIznUgpKVskdoy6Po3GTIfBQcXLEFQJRdaaW"
        const val PAGE_SIZE = 10
    }
}