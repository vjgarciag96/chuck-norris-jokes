package com.vjgarcia.chucknorrisjokes.presentation

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.domain.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

class JokesStore(
    private val jokesReducer: JokesReducer,
    private val loadInitialMiddleware: LoadInitialMiddleware,
    private val loadNextMiddleware: LoadNextMiddleware
) {

    private val state = BehaviorRelay.createDefault<JokesState>(JokesState.Loading)
    private val actionsRelay = PublishRelay.create<JokesAction>()
    private val actionResults = PublishRelay.create<JokesActionResult>()

    fun wire(): Disposable {
        val disposable = CompositeDisposable()

        actionResults
            .withLatestFrom(state, BiFunction<JokesActionResult, JokesState, JokesState> { actionResult, state ->
                jokesReducer.reduce(state, actionResult)
            })
            .distinctUntilChanged()
            .subscribe(state::accept)
            .let(disposable::add)

        Observable.merge(loadInitialMiddleware.bind(actionsRelay), loadNextMiddleware.bind(actionsRelay))
            .subscribe(actionResults::accept)
            .let(disposable::add)

        return disposable
    }

    fun bind(actions: Observable<JokesAction>, render: (JokesState) -> Unit): Disposable {
        val disposable = CompositeDisposable()
        state.observeOn(AndroidSchedulers.mainThread()).subscribe(render).let(disposable::add)
        actions.subscribe(actionsRelay::accept).let(disposable::add)
        return disposable
    }
}