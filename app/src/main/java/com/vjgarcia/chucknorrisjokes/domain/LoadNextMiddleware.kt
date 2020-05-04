package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LoadNextMiddleware(private val jokesRepository: JokesRepository) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> {
        return actions.ofType(LoadNext::class.java).flatMap {
            jokesRepository.random()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map<LoadNextResult> { joke -> LoadNextResult.Success(joke.toJoke()) }
                .onErrorReturn { e -> LoadNextResult.Error(e) }
                .startWith(LoadNextResult.Loading)
        }
    }
}