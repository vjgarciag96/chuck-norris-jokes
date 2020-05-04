package com.vjgarcia.chucknorrisjokes.presentation

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { JokesStore(get(), get(), get()) }
    viewModel { JokesViewModel(get()) }
}