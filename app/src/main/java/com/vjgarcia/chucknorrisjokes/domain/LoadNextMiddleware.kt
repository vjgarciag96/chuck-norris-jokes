package com.vjgarcia.chucknorrisjokes.domain

import io.reactivex.Observable

class LoadNextMiddleware(
    private val loadJokesMiddleware: LoadJokesMiddleware
) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> =
        loadJokesMiddleware.bind(
            actions = actions.ofType(JokesAction.LoadNext::class.java),
            loading = LoadNextResult.Loading,
            next = { jokes -> LoadNextResult.Next(jokes) },
            complete = LoadNextResult.Complete,
            error = { throwable -> LoadNextResult.Error(throwable) }
        )
}