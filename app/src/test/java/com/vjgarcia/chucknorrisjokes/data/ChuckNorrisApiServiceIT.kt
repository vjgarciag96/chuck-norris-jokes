package com.vjgarcia.chucknorrisjokes.data

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class ChuckNorrisApiServiceIT {

    private val mockWebServer = MockWebServer()

    private lateinit var sut: ChuckNorrisApiService

    @BeforeEach
    fun setUp() {
        mockWebServer.start()
        val baseEndpoint = mockWebServer.url("/")

        sut = Retrofit.Builder()
            .baseUrl(baseEndpoint)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ChuckNorrisApiService::class.java)
    }

    @Test
    fun `random joke success response`() {
        givenARandomJokeSuccessfulResponse()
        
        val randomJokeResponse = sut.random().test()
        
        randomJokeResponse.assertValue(JokeDto(
            id = "_en05aqcsvuu3v2vopnoga",
            text = "While urinating, Chuck Norris is easily capable of welding titanium.",
            categories = listOf("science")
        )).dispose()
    }

    private fun givenARandomJokeSuccessfulResponse() {
        val responseBody = """
            {"categories":["science"],"created_at":"2020-01-05 13:42:19.576875","icon_url":"https://assets.chucknorris.host/img/avatar/chuck-norris.png","id":"_en05aqcsvuu3v2vopnoga","updated_at":"2020-01-05 13:42:19.576875","url":"https://api.chucknorris.io/jokes/_en05aqcsvuu3v2vopnoga","value":"While urinating, Chuck Norris is easily capable of welding titanium."}
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseBody)
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}