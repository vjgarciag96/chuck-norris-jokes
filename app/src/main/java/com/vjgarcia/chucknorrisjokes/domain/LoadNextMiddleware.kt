package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LoadNextMiddleware(private val jokesRepository: JokesRepository) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> {
        return actions.ofType(LoadNext::class.java).flatMap {
            jokesRepository.randomStream(NEXT_JOKES_BUFFER_SIZE)
                .buffer(NEXT_JOKES_WINDOW_SIZE)
                .subscribeOn(Schedulers.io())
                .map<LoadNextResult> { joke -> LoadNextResult.Success(joke) }
                .onErrorReturn { e -> LoadNextResult.Error(e) }
                .startWith(LoadNextResult.Loading)
        }
    }

    private companion object {
        const val NEXT_JOKES_BUFFER_SIZE = 100
        const val NEXT_JOKES_WINDOW_SIZE = 20
    }
}