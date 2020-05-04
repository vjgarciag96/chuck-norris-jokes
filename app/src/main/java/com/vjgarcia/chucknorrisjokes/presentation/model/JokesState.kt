package com.vjgarcia.chucknorrisjokes.presentation.model

data class JokesState(
    val jokes: List<Joke>,
    val isLoading: Boolean,
    val loadMoreEnabled: Boolean
) {
    companion object {
        fun initial(): JokesState = JokesState(jokes = emptyList(), isLoading = true, loadMoreEnabled = false)
    }
}

data class Joke(
    val id: String,
    val text: String
)