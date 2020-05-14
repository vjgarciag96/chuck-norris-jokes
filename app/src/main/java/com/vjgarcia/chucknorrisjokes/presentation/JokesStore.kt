package com.vjgarcia.chucknorrisjokes.presentation

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.domain.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import com.vjgarcia.chucknorrisjokes.presentation.reducer.JokesReducer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

class JokesStore(
    private val jokesReducer: JokesReducer,
    private val loadInitialMiddleware: LoadInitialMiddleware,
    private val loadNextMiddleware: LoadNextMiddleware,
    private val refreshMiddleware: RefreshMiddleware,
    private val filterJokesMiddleware: FilterJokesMiddleware
) {

    private val stateRelay = BehaviorRelay.createDefault<JokesState>(JokesState.Loading)
    private val effectRelay = PublishRelay.create<JokesEffects>()
    private val actionsRelay = PublishRelay.create<JokesAction>()
    private val actionResultsRelay = PublishRelay.create<JokesActionResult>()

    fun wire(): Disposable {
        val disposable = CompositeDisposable()

        actionResultsRelay
            .withLatestFrom(stateRelay, BiFunction<JokesActionResult, JokesState, JokesModel> { actionResult, state ->
                jokesReducer.reduce(state, actionResult)
            })
            .distinctUntilChanged()
            .subscribe { (state, effect) ->
                stateRelay.accept(state)
                effectRelay.accept(effect)
            }
            .let(disposable::add)

        Observable.merge(
            loadInitialMiddleware.bind(actionsRelay),
            loadNextMiddleware.bind(actionsRelay),
            refreshMiddleware.bind(actionsRelay),
            filterJokesMiddleware.bind(actionsRelay, stateRelay)
        )
            .subscribe(actionResultsRelay::accept)
            .let(disposable::add)

        return disposable
    }

    fun bind(
        actions: Observable<JokesAction>,
        render: (JokesState) -> Unit,
        executeEffects: (JokesEffects) -> Unit
    ): Disposable {
        val disposable = CompositeDisposable()
        stateRelay.distinctUntilChanged().observeOn(AndroidSchedulers.mainThread()).subscribe(render).let(disposable::add)
        effectRelay.observeOn(AndroidSchedulers.mainThread()).subscribe(executeEffects).let(disposable::add)
        actions.subscribe(actionsRelay::accept).let(disposable::add)
        return disposable
    }
}