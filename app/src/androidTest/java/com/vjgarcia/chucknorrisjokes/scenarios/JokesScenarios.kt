package com.vjgarcia.chucknorrisjokes.scenarios

import com.vjgarcia.chucknorrisjokes.testsplatform.MockedApiServer
import java.net.HttpURLConnection

class JokesScenarios(
    private val mockedApiServer: MockedApiServer
) {

    fun givenInitialJokes() {
        givenAnyJokes(jokesCount = 200)
    }

    fun givenInitialError() {
        mockedApiServer.mockResponse(HttpURLConnection.HTTP_NOT_FOUND)
    }

    fun givenNextJokes() {
        givenAnyJokes(jokesCount = 100)
    }

    fun givenAnyJokesForRefresh() {
        for (idx in 0 until 200) {
            mockedApiServer.mockResponse(
                HttpURLConnection.HTTP_OK,
                """{"categories":["science"],"created_at":"2020-01-05 13:42:19.576875","icon_url":"https://assets.chucknorris.host/img/avatar/chuck-norris.png","id":"_en05aqcsvuu3v2vopnoga","updated_at":"2020-01-05 13:42:19.576875","url":"https://api.chucknorris.io/jokes/_en05aqcsvuu3v2vopnoga","value":"Refreshed"}"""
            )
        }
    }

    private fun givenAnyJokes(jokesCount: Int) {
        for (idx in 0 until jokesCount) {
            mockedApiServer.mockResponse(
                HttpURLConnection.HTTP_OK,
                """{"categories":["science"],"created_at":"2020-01-05 13:42:19.576875","icon_url":"https://assets.chucknorris.host/img/avatar/chuck-norris.png","id":"_en05aqcsvuu3v2vopnoga","updated_at":"2020-01-05 13:42:19.576875","url":"https://api.chucknorris.io/jokes/_en05aqcsvuu3v2vopnoga","value":"$idx While urinating, Chuck Norris is easily capable of welding titanium."}"""
            )
        }
    }
}