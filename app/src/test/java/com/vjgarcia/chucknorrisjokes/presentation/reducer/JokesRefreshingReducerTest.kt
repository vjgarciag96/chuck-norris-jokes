package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.RefreshResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.mothers.anyJoke
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffect
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import junit.framework.TestCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JokesRefreshingReducerTest {

    private val sut = JokesRefreshingReducer()

    @Test
    fun `returns previous content state and DisplayRefreshError effect when action result is refresh error and previous state is start`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Start(anyJokeItems)

        val reducedState = sut.reduce(previousState, RefreshResult.Error(Exception()))

        TestCase.assertEquals(
            JokesModel.from(
                JokesState.Content(anyJokeItems, isLoadingMore = false),
                JokesEffects.from(JokesEffect.DisplayRefreshError)
            ),
            reducedState
        )
    }

    @Test
    fun `returns refresh content state with new jokes if action result is refresh next and previous state is start`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Start(anyJokeItems)
        val anyFreshJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, RefreshResult.Next(anyFreshJokes))

        TestCase.assertEquals(
            JokesModel.from(JokesState.Refreshing.Content(jokes = anyFreshJokes.toJokeContentItems())),
            reducedState
        )
    }


    @Test
    fun `returns refresh content state with old + new loaded jokes if previous state was refresh content and action result is refresh next`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Content(anyJokeItems)
        val anyFreshJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, RefreshResult.Next(anyFreshJokes))

        TestCase.assertEquals(
            JokesModel.from(JokesState.Refreshing.Content(jokes = anyJokeItems + anyFreshJokes.toJokeContentItems())),
            reducedState
        )
    }

    @Test
    fun `returns content state with previous jokes and loading more set to false if previous state is refresh content and action result is refresh error`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Content(anyJokeItems)

        val reducedState = sut.reduce(previousState, RefreshResult.Error(Exception()))

        TestCase.assertEquals(
            JokesModel.from(JokesState.Content(anyJokeItems, isLoadingMore = false)),
            reducedState
        )
    }

    @Test
    fun `returns content state with previous jokes and loading more set to false if previous state is refresh content and action result is refresh complete`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Content(anyJokeItems)

        val reducedState = sut.reduce(previousState, RefreshResult.Complete)

        TestCase.assertEquals(
            JokesModel.from(JokesState.Content(anyJokeItems, isLoadingMore = false)),
            reducedState
        )
    }

    @Test
    fun `throws IllegalStateException if the action result is not valid`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Refreshing.Content(anyJokeItems)

        assertThrows<IllegalStateException> {
            sut.reduce(previousState, RefreshResult.Refreshing)
        }
    }
}