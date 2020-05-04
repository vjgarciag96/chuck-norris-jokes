package com.vjgarcia.chucknorrisjokes.presentation.intent

sealed class JokesIntent
object Start: JokesIntent()
object NextJoke: JokesIntent()
object PreviousJoke: JokesIntent()
object SelectCategory: JokesIntent()