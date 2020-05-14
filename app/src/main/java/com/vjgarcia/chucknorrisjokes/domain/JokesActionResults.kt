package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.repository.Joke

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