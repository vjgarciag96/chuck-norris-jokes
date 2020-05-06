package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LoadInitialMiddleware(private val jokesRepository: JokesRepository) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> {
        return actions.ofType(LoadInitial::class.java).flatMap {
            jokesRepository.randomStream(INITIAL_JOKES_BUFFER)
                .buffer(INITIAL_JOKES_WINDOW_SIZE)
                .subscribeOn(Schedulers.io())
                .map<LoadInitialResult> { joke -> LoadInitialResult.Success(joke) }
                .onErrorReturn { e -> LoadInitialResult.Error(e) }
                .startWith(LoadInitialResult.Loading)
        }
    }

    private companion object {
        const val INITIAL_JOKES_BUFFER = 200
        const val INITIAL_JOKES_WINDOW_SIZE = 20
    }
}