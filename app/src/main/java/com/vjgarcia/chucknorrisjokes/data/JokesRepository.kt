package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.reactivex.Single

class JokesRepository(private val chuckNorrisApiService: ChuckNorrisApiService) {
    fun random(): Single<Joke> = chuckNorrisApiService.random().map { it.toJoke() }
}