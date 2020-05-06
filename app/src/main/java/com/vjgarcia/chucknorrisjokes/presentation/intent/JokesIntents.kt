package com.vjgarcia.chucknorrisjokes.presentation.intent

sealed class JokesIntent
object Start : JokesIntent()
object LoadMoreClicked : JokesIntent()
data class Scrolled(val remainingScrollPercent: Int): JokesIntent()
object RetryClicked: JokesIntent()