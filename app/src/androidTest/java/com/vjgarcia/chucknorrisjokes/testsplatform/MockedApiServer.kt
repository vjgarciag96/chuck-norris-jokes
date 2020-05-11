package com.vjgarcia.chucknorrisjokes.testsplatform

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockedApiServer(
    private val mockWebServer: MockWebServer
) {

    val url: String
        get() = mockWebServer.url("/").toString()

    fun mockResponse(responseCode: Int, body: String) {
        mockWebServer.enqueue(MockResponse().setResponseCode(responseCode).setBody(body))
    }

    fun mockResponse(responseCode: Int) {
        mockWebServer.enqueue(MockResponse().setResponseCode(responseCode))
    }
}