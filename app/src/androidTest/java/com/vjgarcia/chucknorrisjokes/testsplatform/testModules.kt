package com.vjgarcia.chucknorrisjokes.testsplatform

import com.vjgarcia.chucknorrisjokes.scenarios.JokesScenarios
import com.vjgarcia.chucknorrisjokes.screens.JokesScreen
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val testModule: Module
    get() = module {
        factory { JokesScreen() }
        factory { JokesScenarios(get()) }
        single { MockWebServer() }
        factory { MockedApiServer(get()) }
    }


fun mockedApiModule(apiUrl: String): Module = module {
    factory(override = true) {
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
}