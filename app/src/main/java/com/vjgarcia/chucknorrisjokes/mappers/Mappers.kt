package com.vjgarcia.chucknorrisjokes.mappers

import com.vjgarcia.chucknorrisjokes.data.JokeDto
import com.vjgarcia.chucknorrisjokes.domain.JokesAction
import com.vjgarcia.chucknorrisjokes.domain.LoadInitial
import com.vjgarcia.chucknorrisjokes.domain.LoadNext
import com.vjgarcia.chucknorrisjokes.presentation.*

fun JokeDto.toJoke(): Joke = Joke(id, text)

fun JokesUiEvent.toAction(): JokesAction = when(this) {
    NextJoke -> LoadNext
    Start -> LoadInitial
    PreviousJoke -> error("")
    SelectCategory -> error("")
}