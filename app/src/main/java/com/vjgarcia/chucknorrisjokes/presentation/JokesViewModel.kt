package com.vjgarcia.chucknorrisjokes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vjgarcia.chucknorrisjokes.core.presentation.SingleLiveEvent
import com.vjgarcia.chucknorrisjokes.mappers.toAction
import com.vjgarcia.chucknorrisjokes.presentation.intent.JokesIntent
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class JokesViewModel(
    private val store: JokesStore
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val mutableState = MutableLiveData<JokesState>()
    val state: LiveData<JokesState> = mutableState
    private val mutableEffects = SingleLiveEvent<JokesEffects>()
    val effects: LiveData<JokesEffects> = mutableEffects

    init {
        store.wire().let(disposables::add)
    }

    fun bind(jokesIntents: Observable<JokesIntent>) {
        store.bind(
            jokesIntents.map { intent -> intent.toAction() },
            render = mutableState::setValue,
            executeEffects = mutableEffects::setValue
        ).let(disposables::add)
    }

    override fun onCleared() {
        disposables.clear()
    }
}