package com.vjgarcia.chucknorrisjokes.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.intent.JokesIntent
import com.vjgarcia.chucknorrisjokes.presentation.JokesViewModel
import com.vjgarcia.chucknorrisjokes.presentation.intent.NextJoke
import com.vjgarcia.chucknorrisjokes.presentation.intent.Start
import org.koin.android.viewmodel.ext.android.viewModel

class JokesActivity : AppCompatActivity() {

    private val jokesViewModel by viewModel<JokesViewModel>()
    private val jokesIntents = PublishRelay.create<JokesIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)

        val lithoView = findViewById<LithoView>(R.id.lithoContainer)
        val c = ComponentContext(this)
        jokesViewModel.bind(jokesIntents.startWith(Start))
        jokesViewModel.state.observe(::getLifecycle) { viewState ->
            lithoView.setComponentAsync(
                JokesRootComponent
                    .create(c)
                    .state(viewState)
                    .onLoadNextClicked { jokesIntents.accept(NextJoke) }
                    .build()
            )
        }
    }
}