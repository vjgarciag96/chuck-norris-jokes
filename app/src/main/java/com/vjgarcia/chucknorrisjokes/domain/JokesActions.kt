package com.vjgarcia.chucknorrisjokes.domain

sealed class JokesAction
object None: JokesAction()
object LoadNext : JokesAction()
object LoadInitial: JokesAction()