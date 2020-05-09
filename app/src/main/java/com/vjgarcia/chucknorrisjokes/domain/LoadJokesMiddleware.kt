package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LoadJokesMiddleware(
    private val jokesRepository: JokesRepository,
    private val loadJokesConfig: LoadJokesConfig
) {

    fun <A : JokesAction, T : JokesActionResult, L : T, N : T, C : T, E : T> bind(
        actions: Observable<A>,
        loading: L,
        next: (List<Joke>) -> N,
        complete: C,
        error: (Throwable) -> E
    ): Observable<T> = actions.flatMap {
        jokesRepository.randomStream(loadJokesConfig.jokesCount)
            .buffer(loadJokesConfig.chunksSize)
            .subscribeOn(Schedulers.io())
            .map<T>(next)
            .startWith(loading)
            .concatWith(Observable.just(complete))
    }.onErrorReturn(error)
}