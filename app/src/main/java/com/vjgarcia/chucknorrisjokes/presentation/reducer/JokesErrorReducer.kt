package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesErrorReducer {

    fun reduce(previousState: JokesState.Error, actionResult: JokesActionResult): JokesModel = when (actionResult) {
        else -> error("invalid comb of $previousState and $actionResult")
    }
}