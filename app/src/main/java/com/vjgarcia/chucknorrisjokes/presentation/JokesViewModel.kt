package com.vjgarcia.chucknorrisjokes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vjgarcia.chucknorrisjokes.mappers.toAction
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class JokesViewModel(
    private val store: JokesStore
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val mutableState = MutableLiveData<JokesState>()
    val state: LiveData<JokesState> = mutableState

    init {
        store.wire().let(disposables::add)
    }

    fun bind(uiEvents: Observable<JokesUiEvent>) {
        store.bind(uiEvents.map { it.toAction() }, mutableState::setValue).let(disposables::add)
    }

    override fun onCleared() {
        disposables.clear()
    }
}