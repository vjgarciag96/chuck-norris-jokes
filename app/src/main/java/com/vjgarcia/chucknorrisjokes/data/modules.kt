package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    factory { JokesRepository(get()) }
    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
    factory { get<Retrofit>().create(ChuckNorrisApiService::class.java) }
}