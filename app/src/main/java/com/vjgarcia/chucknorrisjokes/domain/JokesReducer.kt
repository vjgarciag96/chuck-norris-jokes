package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.presentation.JokesState

class JokesReducer {

    fun reduce(previousState: JokesState, actionResult: JokesActionResult): JokesState =
        when (actionResult) {
            LoadInitialResult.Loading -> JokesState.initial()
            is LoadInitialResult.Error -> previousState.copy(isLoading = false, loadMoreEnabled = true)
            is LoadInitialResult.Success -> JokesState(jokes = previousState.jokes + actionResult.initialJoke, isLoading = false, loadMoreEnabled = true)
            LoadNextResult.Loading -> previousState.copy(isLoading = true, loadMoreEnabled = false)
            is LoadNextResult.Error -> previousState.copy(isLoading = false, loadMoreEnabled = true)
            is LoadNextResult.Success -> previousState.copy(jokes = previousState.jokes + actionResult.nextJoke, isLoading = false, loadMoreEnabled = true)
        }
}