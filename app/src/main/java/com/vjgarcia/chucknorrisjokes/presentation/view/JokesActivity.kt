package com.vjgarcia.chucknorrisjokes.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.Progress
import com.facebook.yoga.YogaAlign
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
        val jokesListContainer = findViewById<LithoView>(R.id.jokesListContainer)

        val c = ComponentContext(this)
        jokesListContainer.setComponent(Progress.create(c).build())

        jokesViewModel.bind(jokesIntents.startWith(Start))
        jokesViewModel.state.observe(::getLifecycle) { viewState ->
            jokesListContainer.setComponentAsync(
                JokesListComponent
                    .create(c)
                    .state(viewState)
                    .onLoadNextClicked { jokesIntents.accept(NextJoke) }
                    .build()
            )
        }
    }
}