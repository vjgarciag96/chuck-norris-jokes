package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.repository.Joke
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

sealed class JokesActionResult

sealed class LoadInitialResult : JokesActionResult() {
    object Loading : LoadInitialResult()
    data class Error(val error: Throwable) : LoadInitialResult()
    data class Next(val jokes: List<Joke>) : LoadInitialResult()
    object Complete : LoadInitialResult()
}

sealed class LoadNextResult : JokesActionResult() {
    object Loading : LoadNextResult()
    data class Error(val error: Throwable) : LoadNextResult()
    data class Next(val jokes: List<Joke>) : LoadNextResult()
    object Complete : LoadNextResult()
}

sealed class RefreshResult : JokesActionResult() {
    object Refreshing : RefreshResult()
    data class Error(val error: Throwable) : RefreshResult()
    data class Next(val freshJokes: List<Joke>) : RefreshResult()
    object Complete : RefreshResult()
}

data class FilterJokesResult(
    val filteredJokes: List<JokeItem.Content>,
    val selectedCategories: List<String>
) : JokesActionResult()