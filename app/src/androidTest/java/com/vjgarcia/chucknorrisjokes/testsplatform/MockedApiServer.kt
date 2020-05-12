package com.vjgarcia.chucknorrisjokes.testsplatform

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockedApiServer(
    private val mockWebServerProvider: MockWebServerProvider
) {

    private lateinit var mockWebServer: MockWebServer
    private var started = false

    val url: String
        get() = mockWebServer.url("/").toString()

    fun start() {
        if (!started) {
            mockWebServer = mockWebServerProvider.get()
            mockWebServer.start()
            started = true
        }
    }

    fun shutdown() {
        mockWebServer.shutdown()
        started = false
    }

    fun mockResponse(responseCode: Int, body: String) {
        mockWebServer.enqueue(MockResponse().setResponseCode(responseCode).setBody(body))
    }

    fun mockResponse(responseCode: Int) {
        mockWebServer.enqueue(MockResponse().setResponseCode(responseCode))
    }
}