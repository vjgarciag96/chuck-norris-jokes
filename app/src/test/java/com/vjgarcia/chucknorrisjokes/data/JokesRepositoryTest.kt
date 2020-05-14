package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.RxUnitTest
import com.vjgarcia.chucknorrisjokes.data.network.ChuckNorrisApiService
import com.vjgarcia.chucknorrisjokes.data.network.JokeDto
import com.vjgarcia.chucknorrisjokes.data.repository.CategorySelector
import com.vjgarcia.chucknorrisjokes.data.repository.JokesRepository
import com.vjgarcia.chucknorrisjokes.data.storage.ChuckNorrisStorage
import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.jupiter.api.Test

class JokesRepositoryTest : RxUnitTest() {

    private val chuckNorrisApiServiceMock = mockk<ChuckNorrisApiService>()
    private val chuckNorrisStorageMock = mockk<ChuckNorrisStorage>(relaxed = true)
    private val categorySelectorMock = mockk<CategorySelector>()

    private val sut = JokesRepository(
        chuckNorrisApiServiceMock,
        chuckNorrisStorageMock,
        categorySelectorMock
    )

    @Test
    fun `categories successfully fetched from API are stored`() {
        val anyCategories = listOf("a", "b", "c")
        every { chuckNorrisApiServiceMock.categories() } returns Single.just(anyCategories)
        every { categorySelectorMock.select(anyCategories) }.returnsMany("b", "c")
        every { chuckNorrisApiServiceMock.randomByCategory(any()) }.returnsMany(
            Single.just(JokeDto(id = "1", text = "hey", categories = listOf("b"))),
            Single.just(JokeDto(id = "2", text = "que pacha bro", categories = listOf("c")))
        )

        sut.randomStream(2).test().dispose()

        verify { chuckNorrisStorageMock.categories = anyCategories }
    }

    @Test
    fun `categories from storage are returned when fetch from API fails`() {
        val anyCategories = listOf("a", "b", "c")
        every { chuckNorrisApiServiceMock.categories() } returns Single.error(Exception())
        every { chuckNorrisStorageMock.categories } returns anyCategories
        every { categorySelectorMock.select(anyCategories) }.returnsMany("b", "c")
        every { chuckNorrisApiServiceMock.randomByCategory(any()) }.returnsMany(
            Single.just(JokeDto(id = "1", text = "hey", categories = listOf("b"))),
            Single.just(JokeDto(id = "2", text = "que pacha bro", categories = listOf("c")))
        )

        sut.randomStream(2).test().dispose()

        verify { chuckNorrisStorageMock.categories }
    }

    @Test
    fun `correspondent jokes by category are returned`() {
        val anyCategories = listOf("a", "b", "c")
        val anyBJoke = JokeDto(id = "1", text = "hey", categories = listOf("b"))
        val anyCJoke = JokeDto(id = "2", text = "que pacha bro", categories = listOf("c"))
        every { chuckNorrisApiServiceMock.categories() } returns Single.error(Exception())
        every { chuckNorrisStorageMock.categories } returns anyCategories
        every { categorySelectorMock.select(anyCategories) }.returnsMany("b", "c")
        every { chuckNorrisApiServiceMock.randomByCategory("b") } returns Single.just(anyBJoke)
        every { chuckNorrisApiServiceMock.randomByCategory("c") } returns Single.just(anyCJoke)

        val jokesStream = sut.randomStream(2).test()

        jokesStream.assertValues(anyBJoke.toJoke(), anyCJoke.toJoke()).dispose()
    }
}