package com.vjgarcia.chucknorrisjokes.domain

sealed class JokesAction {
    object LoadNext : JokesAction()
    object LoadInitial: JokesAction()
    object Refresh: JokesAction()
    data class UpdateCategoryFilter(val category: String): JokesAction()
}
