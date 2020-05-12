package com.vjgarcia.chucknorrisjokes.testsplatform

import okhttp3.mockwebserver.MockWebServer

class MockWebServerProvider {

    fun get(): MockWebServer = MockWebServer()
}