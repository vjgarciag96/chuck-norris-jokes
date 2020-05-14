package com.vjgarcia.chucknorrisjokes.data.repository

import kotlin.random.Random

class CategorySelector {

    fun select(categories: List<String>): String = categories[Random.Default.nextInt(0, categories.size)]
}