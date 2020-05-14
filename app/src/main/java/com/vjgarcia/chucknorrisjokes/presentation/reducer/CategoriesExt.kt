package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

fun List<JokeItem.Content>.toCategories(): List<String> = flatMap { it.categories }.toSet().sortedBy { it }