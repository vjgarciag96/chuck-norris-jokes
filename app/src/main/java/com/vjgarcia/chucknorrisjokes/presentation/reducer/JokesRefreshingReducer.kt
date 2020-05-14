package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.domain.RefreshResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffect
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesRefreshingReducer {

    fun reduce(previousState: JokesState.Refreshing, actionResult: JokesActionResult): JokesModel =
        when {
            previousState is JokesState.Refreshing.Start && actionResult is RefreshResult.Next -> JokesModel.from(
                JokesState.Refreshing.Content(jokes = actionResult.freshJokes.toJokeContentItems())
            )
            previousState is JokesState.Refreshing.Start && actionResult is RefreshResult.Error -> JokesModel(
                JokesState.Content(
                    jokes = previousState.previousJokes,
                    filteredJokes = previousState.previousFilteredJokes,
                    categories = previousState.previousJokes.toCategories(),
                    selectedCategories = previousState.selectedCategories,
                    isLoadingMore = false
                ),
                JokesEffects.from(JokesEffect.DisplayRefreshError)
            )
            previousState is JokesState.Refreshing.Content && actionResult is RefreshResult.Next -> JokesModel.from(
                previousState.copy(jokes = previousState.jokes + actionResult.freshJokes.toJokeContentItems())
            )
            previousState is JokesState.Refreshing.Content && actionResult is RefreshResult.Error -> JokesModel.from(
                JokesState.Content(
                    jokes = previousState.jokes,
                    filteredJokes = previousState.jokes,
                    categories = previousState.jokes.toCategories(),
                    selectedCategories = emptyList(),
                    isLoadingMore = false
                )
            )
            previousState is JokesState.Refreshing.Content && actionResult is RefreshResult.Complete -> JokesModel.from(
                JokesState.Content(
                    jokes = previousState.jokes,
                    filteredJokes = previousState.jokes,
                    categories = previousState.jokes.toCategories(),
                    selectedCategories = emptyList(),
                    isLoadingMore = false
                )
            )
            else -> error("invalid combination of $previousState and $actionResult")
        }
}