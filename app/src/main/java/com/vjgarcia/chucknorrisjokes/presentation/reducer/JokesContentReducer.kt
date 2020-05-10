package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.domain.LoadInitialResult
import com.vjgarcia.chucknorrisjokes.domain.LoadNextResult
import com.vjgarcia.chucknorrisjokes.domain.RefreshResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffect
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesContentReducer {

    fun reduce(previousState: JokesState.Content, actionResult: JokesActionResult): JokesModel =
        when (actionResult) {
            is LoadInitialResult.Error -> initialError(previousState)
            is LoadInitialResult.Next -> nextContent(previousState, actionResult.jokes)
            LoadInitialResult.Complete -> completed(previousState)
            LoadNextResult.Loading -> loadingMore(previousState)
            is LoadNextResult.Error -> errorWithContent(previousState)
            is LoadNextResult.Next -> nextContent(previousState, actionResult.jokes)
            LoadNextResult.Complete -> completed(previousState)
            RefreshResult.Refreshing -> refreshing(previousState)
            else -> error("invalid combination of $previousState and $actionResult")
        }

    private fun initialError(previousState: JokesState.Content): JokesModel = if (previousState.jokes.isEmpty()) {
        JokesModel.from(JokesState.Error)
    } else {
        errorWithContent(previousState)
    }

    private fun nextContent(previousState: JokesState.Content, nextJokes: List<Joke>): JokesModel = JokesModel.from(
        previousState.copy(jokes = previousState.jokes + nextJokes.toJokeContentItems(), isLoadingMore = true)
    )

    private fun completed(previousState: JokesState.Content): JokesModel = JokesModel.from(previousState.copy(isLoadingMore = false))

    private fun loadingMore(previousState: JokesState.Content): JokesModel = JokesModel.from(previousState.copy(isLoadingMore = true))

    private fun errorWithContent(previousState: JokesState.Content): JokesModel = JokesModel.from(
        previousState.copy(isLoadingMore = false),
        JokesEffects.from(JokesEffect.DisplayLoadNextError)
    )

    private fun refreshing(previousState: JokesState.Content): JokesModel = JokesModel.from(JokesState.Refreshing.Start(previousState.jokes))
}