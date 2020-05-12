package com.vjgarcia.chucknorrisjokes.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.view.JokeItemViewHolder
import com.vjgarcia.chucknorrisjokes.testsplatform.JokeTextAssertion.Companion.withJokeTextAtPosition
import com.vjgarcia.chucknorrisjokes.testsplatform.JokesLoadMoreViewHolderMatcher.Companion.jokesLoadMore
import com.vjgarcia.chucknorrisjokes.testsplatform.RecyclerViewItemCountAssertion
import com.vjgarcia.chucknorrisjokes.testsplatform.RecyclerViewItemCountAssertion.Companion.withItemCount

class JokesScreen {

    fun scrollToLoadMoreButton(): JokesScreen = apply {
        onView(withId(R.id.jokes)).perform(scrollToHolder(jokesLoadMore()))
    }

    fun clickOnLoadMoreButton(): JokesScreen = apply {
        onView(withId(R.id.loadMore)).perform(click())
    }

    fun swipeToRefresh(): JokesScreen = apply {
        onView(withId(R.id.jokes)).perform(scrollToPosition<JokeItemViewHolder>(0))
        onView(withId(R.id.jokes)).perform(swipeDown())
    }

    fun verifyInitialJokesDisplayed(): JokesScreen = apply {
        onView(withId(R.id.jokes))
            .check(matches(isDisplayed()))
            .check(withItemCount(201))
    }

    fun verifyNextJokesDisplayed(): JokesScreen = apply {
        onView(withId(R.id.jokes))
            .check(matches(isDisplayed()))
            .check(withItemCount(301))
    }

    fun verifyRetryDisplayed(): JokesScreen = apply {
        onView(withId(R.id.retry)).check(matches(isDisplayed()))
    }

    fun verifyJokesRefreshed(): JokesScreen = apply {
        onView(withId(R.id.jokes))
            .check(withJokeTextAtPosition("Refreshed", 0))
            .check(withJokeTextAtPosition("Refreshed", 199))
            .check(withItemCount(201))
    }
}