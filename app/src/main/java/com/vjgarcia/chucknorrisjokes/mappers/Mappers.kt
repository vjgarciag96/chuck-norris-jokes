package com.vjgarcia.chucknorrisjokes.mappers

import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.data.JokeDto
import com.vjgarcia.chucknorrisjokes.domain.JokesAction
import com.vjgarcia.chucknorrisjokes.domain.LoadInitial
import com.vjgarcia.chucknorrisjokes.domain.LoadNext
import com.vjgarcia.chucknorrisjokes.domain.None
import com.vjgarcia.chucknorrisjokes.presentation.intent.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

fun JokeDto.toJoke(): Joke = Joke(id, text)
fun Joke.toJokeItemContent(): JokeItem.Content = JokeItem.Content(id, text)

fun JokesIntent.toAction(): JokesAction = when (this) {
    LoadMoreClicked -> LoadNext
    Start -> LoadInitial
    is Scrolled -> if (remainingScrollPercent > 30) None else LoadNext
    RetryClicked -> LoadInitial
}