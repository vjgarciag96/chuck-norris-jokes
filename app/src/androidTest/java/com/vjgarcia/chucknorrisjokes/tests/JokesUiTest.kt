package com.vjgarcia.chucknorrisjokes.tests

import com.vjgarcia.chucknorrisjokes.presentation.view.JokesActivity
import com.vjgarcia.chucknorrisjokes.scenarios.JokesScenarios
import com.vjgarcia.chucknorrisjokes.testsplatform.UITest
import org.junit.Test
import org.koin.test.inject

class JokesUiTest : UITest<JokesActivity>(JokesActivity::class.java) {

    private val jokesScenarios by inject<JokesScenarios>()

    @Test
    fun loadsInitialJokes() {
        jokesScenarios.givenInitialJokes()

        givenThatCurrentScreenIsJokes()
            .verifyJokesDisplayed()
    }

    @Test
    fun errorBeforeLoadingAnyJokes() {
        jokesScenarios.givenInitialError()

        givenThatCurrentScreenIsJokes()
            .verifyRetryDisplayed()
    }
}