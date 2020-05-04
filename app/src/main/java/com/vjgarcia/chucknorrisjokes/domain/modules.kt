package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.presentation.JokesReducer
import org.koin.dsl.module

val domainModule = module {
    factory { JokesReducer() }
    factory { LoadNextMiddleware(get()) }
    factory { LoadInitialMiddleware(get()) }
}