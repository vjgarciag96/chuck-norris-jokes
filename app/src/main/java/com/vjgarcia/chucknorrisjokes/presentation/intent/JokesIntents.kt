package com.vjgarcia.chucknorrisjokes.presentation.intent

sealed class JokesIntent
object Start : JokesIntent()
object LoadMoreClicked : JokesIntent()
object RetryClicked: JokesIntent()
object RefreshRequested: JokesIntent()