package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET

interface ChuckNorrisApiService {
    @GET(BuildConfig.RANDOM_PATH)
    fun random(): Single<JokeDto>
}