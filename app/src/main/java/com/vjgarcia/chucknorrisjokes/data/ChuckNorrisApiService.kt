package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApiService {
    @GET(BuildConfig.RANDOM_PATH)
    fun random(): Single<JokeDto>

    @GET(BuildConfig.CATEGORIES_PATH)
    fun categories(): Single<List<String>>

    @GET(BuildConfig.RANDOM_PATH)
    fun randomByCategory(@Query("category") category: String): Single<JokeDto>
}