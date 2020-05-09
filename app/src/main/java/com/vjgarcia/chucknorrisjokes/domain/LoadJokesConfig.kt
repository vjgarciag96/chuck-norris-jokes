package com.vjgarcia.chucknorrisjokes.domain

data class LoadJokesConfig(
    val jokesCount: Int,
    val chunksSize: Int
)