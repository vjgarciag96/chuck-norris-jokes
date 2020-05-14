package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.BuildConfig
import com.vjgarcia.chucknorrisjokes.core.ioc.KoinConfiguration
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val domainModule = module(override = KoinConfiguration.overridable) {
    factory { (loadJokesConfig: LoadJokesConfig) -> LoadJokesMiddleware(get(), loadJokesConfig) }
    factory { LoadNextMiddleware(get { parametersOf(LoadJokesConfig(BuildConfig.NEXT_JOKES_COUNT, BuildConfig.NEXT_JOKES_CHUNK_SIZE)) }) }
    factory { LoadInitialMiddleware(get { parametersOf(LoadJokesConfig(BuildConfig.INITIAL_JOKES_COUNT, BuildConfig.INITIAL_JOKES_CHUNK_SIZE)) }) }
    factory { RefreshMiddleware(get { parametersOf(LoadJokesConfig(BuildConfig.INITIAL_JOKES_COUNT, BuildConfig.INITIAL_JOKES_CHUNK_SIZE)) }) }
}