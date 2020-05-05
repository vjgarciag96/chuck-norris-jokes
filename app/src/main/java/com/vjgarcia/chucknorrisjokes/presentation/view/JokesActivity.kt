package com.vjgarcia.chucknorrisjokes.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.JokesViewModel
import com.vjgarcia.chucknorrisjokes.presentation.intent.JokesIntent
import com.vjgarcia.chucknorrisjokes.presentation.intent.NextJoke
import com.vjgarcia.chucknorrisjokes.presentation.intent.Start
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class JokesActivity : AppCompatActivity() {

    private val jokesIntents = PublishRelay.create<JokesIntent>()
    private val loadMoreClickListener = object : LoadMoreClickListener {
        override fun onClick() {
            jokesIntents.accept(NextJoke)
        }
    }

    private val jokesViewModel by viewModel<JokesViewModel>()
    private val jokeAdapter by inject<JokeAdapter> { parametersOf(loadMoreClickListener) }

    private lateinit var jokesSkeleton: View
    private lateinit var jokesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)
        setUpViewModel()
        setUpView()
    }

    private fun setUpViewModel() {
        jokesViewModel.bind(jokesIntents.startWith(Start))
        jokesViewModel.state.observe(::getLifecycle, ::onJokesState)
    }

    private fun setUpView() {
        jokesRecyclerView = findViewById(R.id.jokes)
        jokesRecyclerView.adapter = jokeAdapter
        jokesSkeleton = findViewById(R.id.jokesSkeleton)
    }

    private fun onJokesState(jokesState: JokesState) {
        jokesSkeleton.isGone = !jokesState.isLoading
        jokesRecyclerView.isGone = jokesState !is JokesState.Content
        if (jokesState is JokesState.Content) {
            jokeAdapter.submitList(jokesState.jokeItems)
        }
    }
}