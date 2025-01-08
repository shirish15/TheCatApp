package com.example.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Breed(
    val name: String,
    val image: ImageData?,
    val temperament: String?,
    val origin: String?,
    @Json(name = "life_span") val lifeSpan: String?,
    @Json(name = "wikipedia_url") val wikiUrl: String?,
    val description: String?,
)

@JsonClass(generateAdapter = true)
data class ImageData(
    val url: String?,
)