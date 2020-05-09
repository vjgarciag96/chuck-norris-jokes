package com.vjgarcia.chucknorrisjokes.presentation.reducer

import com.vjgarcia.chucknorrisjokes.domain.LoadInitialResult
import com.vjgarcia.chucknorrisjokes.domain.LoadNextResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeContentItems
import com.vjgarcia.chucknorrisjokes.mothers.anyJoke
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesModel
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.lang.IllegalStateException

class JokesLoadingReducerTest {

    private val sut = JokesLoadingReducer()

    @Test
    fun `returns previous state if action result is a load initial result loading`() {
        val previousState = JokesState.Loading

        val reducedState = sut.reduce(previousState, LoadInitialResult.Loading)

        assertEquals(JokesModel.from(previousState), reducedState)
    }

    @Test
    fun `returns an error state if action result is a load initial result error`() {
        val anyLoadInitialResultError = LoadInitialResult.Error(Exception())

        val reducedState = sut.reduce(JokesState.Loading, anyLoadInitialResultError)

        assertEquals(JokesModel.from(JokesState.Error), reducedState)
    }

    @Test
    fun `returns a content state with next jokes and loading more to true if action result is a load initial result next`() {
        val anyJokes = listOf(anyJoke(id = "1"), anyJoke(id = "2"))
        val anyLoadInitialResultNext = LoadInitialResult.Next(anyJokes)

        val reducedState = sut.reduce(JokesState.Loading, anyLoadInitialResultNext)

        assertEquals(JokesModel.from(JokesState.Content(anyJokes.toJokeContentItems(), isLoadingMore = true)), reducedState)
    }

    @Test
    fun `throws an IllegalStateException when the action result is invalid`() {
        val anyInvalidActionResult = LoadNextResult.Complete

        assertThrows<IllegalStateException> {
            sut.reduce(JokesState.Loading, anyInvalidActionResult)
        }
    }
}