package com.vjgarcia.chucknorrisjokes.presentation

sealed class JokesUiEvent
object Start: JokesUiEvent()
object NextJoke: JokesUiEvent()
object PreviousJoke: JokesUiEvent()
object SelectCategory: JokesUiEvent()