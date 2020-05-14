package com.vjgarcia.chucknorrisjokes.mappers

import com.vjgarcia.chucknorrisjokes.data.repository.Joke
import com.vjgarcia.chucknorrisjokes.data.network.JokeDto
import com.vjgarcia.chucknorrisjokes.domain.JokesAction
import com.vjgarcia.chucknorrisjokes.domain.LoadInitial
import com.vjgarcia.chucknorrisjokes.domain.LoadNext
import com.vjgarcia.chucknorrisjokes.domain.Refresh
import com.vjgarcia.chucknorrisjokes.presentation.intent.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

fun JokeDto.toJoke(): Joke = Joke(id, text)
fun Joke.toJokeItemContent(): JokeItem.Content = JokeItem.Content(id, text)
fun Iterable<Joke>.toJokeContentItems(): List<JokeItem.Content> = map { it.toJokeItemContent() }

fun JokesIntent.toAction(): JokesAction = when (this) {
    LoadMoreClicked -> LoadNext
    Start -> LoadInitial
    RetryClicked -> LoadInitial
    RefreshRequested -> Refresh
}