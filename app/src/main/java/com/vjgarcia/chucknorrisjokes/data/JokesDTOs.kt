package com.vjgarcia.chucknorrisjokes.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeDto(
    @Json(name = "id") val id: String,
    @Json(name = "value") val text: String,
    @Json(name = "categories") val categories: List<String>
)