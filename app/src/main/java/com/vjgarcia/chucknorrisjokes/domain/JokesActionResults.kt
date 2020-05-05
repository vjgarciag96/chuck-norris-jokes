package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.data.Joke

sealed class JokesActionResult

sealed class LoadInitialResult: JokesActionResult() {
    object Loading: LoadInitialResult()
    data class Error(val error: Throwable): LoadInitialResult()
    data class Success(val initialJokes: List<Joke>): LoadInitialResult()
}

sealed class LoadNextResult: JokesActionResult() {
    object Loading: LoadNextResult()
    data class Error(val error: Throwable): LoadNextResult()
    data class Success(val nextJoke: Joke): LoadNextResult()
}