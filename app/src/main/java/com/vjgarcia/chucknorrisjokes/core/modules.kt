package com.vjgarcia.chucknorrisjokes.core

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vjgarcia.chucknorrisjokes.core.ioc.KoinConfiguration
import com.vjgarcia.chucknorrisjokes.core.storage.KeyValueStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private object SharedPreferencesConstants {
    const val NAME = "chuckNorris:sharedPreferencesName"
}

val coreModule = module(override = KoinConfiguration.overridable) {
    factory { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    factory {
        androidContext().getSharedPreferences(
            SharedPreferencesConstants.NAME,
            Context.MODE_PRIVATE
        )
    }
    factory { KeyValueStorage(get(), get()) }
}