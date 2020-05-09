package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesReducer(
    private val jokesLoadingReducer: JokesLoadingReducer,
    private val jokesContentReducer: JokesContentReducer,
    private val jokesErrorReducer: JokesErrorReducer
) {

    fun reduce(previousState: JokesState, actionResult: JokesActionResult): JokesModel =
        when (previousState) {
            is JokesState.Loading -> jokesLoadingReducer.reduce(previousState, actionResult)
            is JokesState.Content -> jokesContentReducer.reduce(previousState, actionResult)
            is JokesState.Error -> jokesErrorReducer.reduce(previousState, actionResult)
        }
}