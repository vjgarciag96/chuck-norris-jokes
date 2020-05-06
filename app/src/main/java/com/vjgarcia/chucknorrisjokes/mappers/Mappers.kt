package com.vjgarcia.chucknorrisjokes.mappers

import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.data.JokeDto
import com.vjgarcia.chucknorrisjokes.domain.JokesAction
import com.vjgarcia.chucknorrisjokes.domain.LoadInitial
import com.vjgarcia.chucknorrisjokes.domain.LoadNext
import com.vjgarcia.chucknorrisjokes.presentation.intent.JokesIntent
import com.vjgarcia.chucknorrisjokes.presentation.intent.LoadMoreClicked
import com.vjgarcia.chucknorrisjokes.presentation.intent.RetryClicked
import com.vjgarcia.chucknorrisjokes.presentation.intent.Start
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

fun JokeDto.toJoke(): Joke = Joke(id, text)
fun Joke.toJokeItemContent(): JokeItem.Content = JokeItem.Content(id, text)

fun JokesIntent.toAction(): JokesAction = when (this) {
    LoadMoreClicked -> LoadNext
    Start -> LoadInitial
    RetryClicked -> LoadInitial
}