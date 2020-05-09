package com.vjgarcia.chucknorrisjokes.domain

import io.reactivex.Observable

class RefreshMiddleware(
    private val loadJokesMiddleware: LoadJokesMiddleware
) {

    fun bind(actions: Observable<JokesAction>): Observable<JokesActionResult> =
        loadJokesMiddleware.bind(
            actions = actions.ofType(Refresh::class.java),
            loading = RefreshResult.Refreshing,
            next = { jokes -> RefreshResult.Next(jokes) },
            complete = RefreshResult.Complete,
            error = { throwable -> RefreshResult.Error(throwable) }
        )
}