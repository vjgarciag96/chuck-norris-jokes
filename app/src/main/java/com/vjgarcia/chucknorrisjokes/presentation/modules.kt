package com.vjgarcia.chucknorrisjokes.presentation

import com.vjgarcia.chucknorrisjokes.presentation.reducer.*
import com.vjgarcia.chucknorrisjokes.presentation.view.JokeAdapter
import com.vjgarcia.chucknorrisjokes.presentation.view.JokeItemDiffCallback
import com.vjgarcia.chucknorrisjokes.presentation.view.LoadMoreClickListener
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { JokesStore(get(), get(), get(), get()) }
    viewModel { JokesViewModel(get()) }
    factory { JokeItemDiffCallback() }
    factory { (loadMoreClickListener: LoadMoreClickListener) -> JokeAdapter(get(), loadMoreClickListener) }
    factory { JokesLoadingReducer() }
    factory { JokesContentReducer() }
    factory { JokesErrorReducer() }
    factory { JokesRefreshingReducer() }
    factory { JokesReducer(get(), get(), get(), get()) }
}