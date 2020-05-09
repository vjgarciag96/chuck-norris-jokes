package com.vjgarcia.chucknorrisjokes.mothers

import com.vjgarcia.chucknorrisjokes.data.Joke

const val ANY_ID = "rukaId324234"
const val ANY_TEXT = "Erase una vez en la Mancha..."
fun anyJoke(id: String = ANY_ID, text: String = ANY_TEXT): Joke = Joke(id, text)