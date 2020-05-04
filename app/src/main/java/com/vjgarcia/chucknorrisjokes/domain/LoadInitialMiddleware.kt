package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LoadInitialMiddleware(private val jokesRepository: JokesRepository) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> {
        return actions.ofType(LoadInitial::class.java).flatMap {
            jokesRepository.random()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map<LoadInitialResult> { joke -> LoadInitialResult.Success(joke.toJoke()) }
                .onErrorReturn { e -> LoadInitialResult.Error(e) }
                .startWith(LoadInitialResult.Loading)
        }
    }
}