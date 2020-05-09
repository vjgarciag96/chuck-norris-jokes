package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.RxUnitTest
import com.vjgarcia.chucknorrisjokes.data.Joke
import com.vjgarcia.chucknorrisjokes.data.JokesRepository
import com.vjgarcia.chucknorrisjokes.mothers.anyJoke
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.jupiter.api.Test

class RefreshMiddlewareTest: RxUnitTest() {

    private val jokesRepositoryMock = mockk<JokesRepository>()
    private val loadJokesConfig = LoadJokesConfig(ANY_JOKES_COUNT, ANY_CHUNKS_SIZE)
    private val loadJokesMiddleware = LoadJokesMiddleware(jokesRepositoryMock, loadJokesConfig)

    private val sut = RefreshMiddleware(loadJokesMiddleware)

    @Test
    fun `doesn't emit jokes if an error happens`() {
        val anyException = Exception()
        val anyJoke = anyJoke()
        val randomJokesStream = Observable.error<Joke>(anyException).concatWith(Observable.just(anyJoke))
        every { jokesRepositoryMock.randomStream(ANY_JOKES_COUNT) } returns randomJokesStream

        val bindTestObservable = sut.bind(Observable.just(Refresh)).test()

        bindTestObservable
            .assertValues(RefreshResult.Refreshing, RefreshResult.Error(anyException))
            .assertComplete()
            .dispose()
    }

    @Test
    fun `emits jokes chunks until an error happens`() {
        val anyException = Exception()
        val anyJoke1 = anyJoke(id = "1")
        val anyJoke2 = anyJoke(id = "2")
        val randomJokesStream = Observable.just(anyJoke1, anyJoke2).concatWith(Observable.error(anyException))
        every { jokesRepositoryMock.randomStream(ANY_JOKES_COUNT) } returns randomJokesStream

        val bindTestObservable = sut.bind(Observable.just(Refresh)).test()

        bindTestObservable
            .assertValues(
                RefreshResult.Refreshing,
                RefreshResult.Next(listOf(anyJoke1)),
                RefreshResult.Next(listOf(anyJoke2)),
                RefreshResult.Error(anyException)
            )
            .assertComplete()
            .dispose()
    }

    @Test
    fun `finishes after emitting all the jokes chunks`() {
        val anyJoke1 = anyJoke(id = "1")
        val anyJoke2 = anyJoke(id = "2")
        val anyJoke3 = anyJoke(id = "3")
        val randomJokesStream = Observable.just(anyJoke1, anyJoke2, anyJoke3)
        every { jokesRepositoryMock.randomStream(ANY_JOKES_COUNT) } returns randomJokesStream

        val bindTestObservable = sut.bind(Observable.just(Refresh)).test()

        bindTestObservable
            .assertValues(
                RefreshResult.Refreshing,
                RefreshResult.Next(listOf(anyJoke1)),
                RefreshResult.Next(listOf(anyJoke2)),
                RefreshResult.Next(listOf(anyJoke3)),
                RefreshResult.Complete
            )
            .assertComplete()
            .dispose()
    }

    @Test
    fun `does nothing if the input actions don't contain Refresh`() {
        val anyJoke = anyJoke()
        val randomJokesStream = Observable.just(anyJoke)
        every { jokesRepositoryMock.randomStream(ANY_JOKES_COUNT) } returns randomJokesStream

        val bindTestObservable = sut.bind(Observable.just(LoadInitial)).test()

        bindTestObservable
            .assertNoValues()
            .assertComplete()
            .dispose()
    }

    private companion object {
        const val ANY_JOKES_COUNT = 3
        const val ANY_CHUNKS_SIZE = 1
    }
}