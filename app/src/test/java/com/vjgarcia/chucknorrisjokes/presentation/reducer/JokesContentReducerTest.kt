package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.LoadInitialResult
import com.vjgarcia.chucknorrisjokes.domain.LoadNextResult
import com.vjgarcia.chucknorrisjokes.domain.RefreshResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.mothers.anyJoke
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffect
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.lang.IllegalStateException

class JokesContentReducerTest {

    private val sut = JokesContentReducer()

    @Test
    fun `returns error state if action result is initial error and previous state doesn't have jokes`() {
        val previousState = JokesState.Content(jokes = emptyList(), isLoadingMore = false)

        val reducedState = sut.reduce(previousState, LoadInitialResult.Error(Exception()))

        assertEquals(JokesModel.from(JokesState.Error), reducedState)
    }

    @Test
    fun `returns content state with loading more set to false and DisplayLoadNextError effect if action result is initial error and previous state has jokes`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)

        val reducedState = sut.reduce(previousState, LoadInitialResult.Error(Exception()))

        assertEquals(
            JokesModel.from(
                previousState.copy(isLoadingMore = false),
                JokesEffects.from(JokesEffect.DisplayLoadNextError)
            ),
            reducedState
        )
    }

    @Test
    fun `returns content state with old + new jokes and loading more set to true when action result is initial next`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)
        val anyNewJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, LoadInitialResult.Next(anyNewJokes))

        assertEquals(
            JokesModel.from(
                previousState.copy(
                    jokes = previousState.jokes + anyNewJokes.toJokeContentItems(),
                    isLoadingMore = true
                )
            ),
            reducedState
        )
    }

    @Test
    fun `returns previous content state with loading more set to false when action result is initial complete`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)

        val reducedState = sut.reduce(previousState, LoadInitialResult.Complete)

        assertEquals(JokesModel.from(previousState.copy(isLoadingMore = false)), reducedState)
    }

    @Test
    fun `returns previous content state with loading more set to true when action result is next loading`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = false)

        val reducedState = sut.reduce(previousState, LoadNextResult.Loading)

        assertEquals(JokesModel.from(previousState.copy(isLoadingMore = true)), reducedState)
    }

    @Test
    fun `returns previous content state with loading more set to false and DisplayLoadNextError effect when action result is next error`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)

        val reducedState = sut.reduce(previousState, LoadNextResult.Error(Exception()))

        assertEquals(
            JokesModel.from(
                previousState.copy(isLoadingMore = false),
                JokesEffects.from(JokesEffect.DisplayLoadNextError)
            ),
            reducedState
        )
    }

    @Test
    fun `returns content state with old + new jokes and loading more set to true when action result is next next`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)
        val anyNewJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, LoadNextResult.Next(anyNewJokes))

        assertEquals(
            JokesModel.from(
                previousState.copy(
                    jokes = previousState.jokes + anyNewJokes.toJokeContentItems(),
                    isLoadingMore = true
                )
            ),
            reducedState
        )
    }

    @Test
    fun `returns previous content state with loading more set to false when action result is next complete`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true)

        val reducedState = sut.reduce(previousState, LoadNextResult.Complete)

        assertEquals(JokesModel.from(previousState.copy(isLoadingMore = false)), reducedState)
    }

    @Test
    fun `returns previous content state with isRefreshing set to true when action result is refreshing`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = false)

        val reducedState = sut.reduce(previousState, RefreshResult.Refreshing)

        assertEquals(JokesModel.from(previousState.copy(isRefreshing = true)), reducedState)
    }

    @Test
    fun `returns previous content state with isRefreshing set to false and DisplayRefreshError effect when action result is refresh error`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = false, isRefreshing = true)

        val reducedState = sut.reduce(previousState, RefreshResult.Error(Exception()))

        assertEquals(
            JokesModel.from(
                previousState.copy(isRefreshing = false),
                JokesEffects.from(JokesEffect.DisplayRefreshError)
            ),
            reducedState
        )
    }

    @Test
    fun `returns content state with new loaded jokes, is refreshing and is loading more set to true if previous state was not refreshing and action result is refresh next`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = false, isRefreshing = false)
        val anyFreshJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, RefreshResult.Next(anyFreshJokes))

        assertEquals(
            JokesModel.from(
                JokesState.Content(
                    jokes = anyFreshJokes.toJokeContentItems(),
                    isLoadingMore = true,
                    isRefreshing = true
                )
            ),
            reducedState
        )
    }

    @Test
    fun `returns content state with old + new loaded jokes, is refreshing and is loading more set to true if previous state was refreshing and action result is refresh next`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = false, isRefreshing = true)
        val anyFreshJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))

        val reducedState = sut.reduce(previousState, RefreshResult.Next(anyFreshJokes))

        assertEquals(
            JokesModel.from(
                JokesState.Content(
                    jokes = anyJokeItems + anyFreshJokes.toJokeContentItems(),
                    isLoadingMore = true,
                    isRefreshing = true
                )
            ),
            reducedState
        )
    }

    @Test
    fun `returns previous state with is refreshing and is loading more set to false if action result is refresh complete`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true, isRefreshing = true)

        val reducedState = sut.reduce(previousState, RefreshResult.Complete)

        assertEquals(
            JokesModel.from(previousState.copy(isLoadingMore = false, isRefreshing = false)),
            reducedState
        )
    }

    @Test
    fun `throws IllegalStateException if the action result is not valid`() {
        val anyJokeItems = listOf(anyJoke()).toJokeContentItems()
        val previousState = JokesState.Content(jokes = anyJokeItems, isLoadingMore = true, isRefreshing = true)

        assertThrows<IllegalStateException> {
            sut.reduce(previousState, LoadInitialResult.Loading)
        }
    }
}