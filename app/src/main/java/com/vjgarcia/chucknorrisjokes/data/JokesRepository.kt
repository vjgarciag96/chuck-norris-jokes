package com.vjgarcia.chucknorrisjokes.data

import io.reactivex.Single

class JokesRepository(private val chuckNorrisApiService: ChuckNorrisApiService) {
    fun random(): Single<JokeDto> = chuckNorrisApiService.random()
}