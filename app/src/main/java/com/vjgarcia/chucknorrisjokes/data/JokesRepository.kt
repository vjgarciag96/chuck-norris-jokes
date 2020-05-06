package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class JokesRepository(private val chuckNorrisApiService: ChuckNorrisApiService) {

    fun random(): Single<Joke> = chuckNorrisApiService.random().map { it.toJoke() }

    fun randomStream(jokesCount: Int): Observable<Joke> = Observable.range(0, jokesCount - 1).flatMap {
        random().toObservable().subscribeOn(Schedulers.io())
    }
}