package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.domain.LoadInitialResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesLoadingReducer {

    fun reduce(previousState: JokesState.Loading, actionResult: JokesActionResult): JokesModel =
        when (actionResult) {
            is LoadInitialResult.Loading -> JokesModel.from(previousState)
            is LoadInitialResult.Error -> JokesModel.from(JokesState.Error)
            is LoadInitialResult.Next -> JokesModel.from(JokesState.Content(
                jokes = actionResult.jokes.toJokeContentItems(),
                isLoadingMore = false
            ))
            else -> error("invalid actionResult $actionResult for loading state")
        }
}