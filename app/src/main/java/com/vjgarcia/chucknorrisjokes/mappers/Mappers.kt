package com.vjgarcia.chucknorrisjokes.mappers

import com.vjgarcia.chucknorrisjokes.data.repository.Joke
import com.vjgarcia.chucknorrisjokes.data.network.JokeDto
import com.vjgarcia.chucknorrisjokes.domain.JokesAction
import com.vjgarcia.chucknorrisjokes.presentation.intent.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

fun JokeDto.toJoke(): Joke = Joke(id, text, categories)
fun Joke.toJokeItemContent(): JokeItem.Content = JokeItem.Content(id, text, categories)
fun Iterable<Joke>.toJokeContentItems(): List<JokeItem.Content> = map { it.toJokeItemContent() }

fun JokesIntent.toAction(): JokesAction = when (this) {
    JokesIntent.LoadMoreClicked -> JokesAction.LoadNext
    JokesIntent.Start -> JokesAction.LoadInitial
    JokesIntent.RetryClicked -> JokesAction.LoadInitial
    JokesIntent.RefreshRequested -> JokesAction.Refresh
    is JokesIntent.CategorySelected -> JokesAction.UpdateCategoryFilter(category)
}