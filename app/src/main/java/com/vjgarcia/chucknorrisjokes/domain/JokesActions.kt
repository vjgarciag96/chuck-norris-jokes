package com.vjgarcia.chucknorrisjokes.domain

sealed class JokesAction
object LoadNext : JokesAction()
object LoadInitial: JokesAction()