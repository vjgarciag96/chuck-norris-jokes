package com.vjgarcia.chucknorrisjokes.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.vjgarcia.chucknorrisjokes.R

class JokesScreen {

    fun verifyJokesDisplayed(): JokesScreen = apply {
        onView(withId(R.id.jokes)).check(matches(isDisplayed()))
    }

    fun verifyRetryDisplayed(): JokesScreen = apply {
        onView(withId(R.id.retry)).check(matches(isDisplayed()))
    }
}