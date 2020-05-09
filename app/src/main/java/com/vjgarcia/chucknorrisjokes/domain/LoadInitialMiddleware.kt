package com.vjgarcia.chucknorrisjokes.domain

import io.reactivex.Observable

class LoadInitialMiddleware(
    private val loadJokesMiddleware: LoadJokesMiddleware
) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> =
        loadJokesMiddleware.bind(
            actions = actions.ofType(LoadInitial::class.java),
            loading = LoadInitialResult.Loading,
            next = { jokes -> LoadInitialResult.Next(jokes) },
            complete = LoadInitialResult.Complete,
            error = { throwable -> LoadInitialResult.Error(throwable) }
        )
}