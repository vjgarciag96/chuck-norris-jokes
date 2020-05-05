package com.vjgarcia.chucknorrisjokes.presentation.model

sealed class JokesState {
    object Loading : JokesState()
    data class Content(val jokeItems: List<JokeItem>) : JokesState()
    object Error : JokesState()

    val isLoading: Boolean
        get() = this is Loading
    val isError: Boolean
        get() = this is Error
}

sealed class JokeItem {
    object Skeleton : JokeItem()
    data class Content(val id: String, val text: String) : JokeItem()
    object LoadMore : JokeItem()
}