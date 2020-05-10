package com.vjgarcia.chucknorrisjokes.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.core.exhaustive
import com.vjgarcia.chucknorrisjokes.presentation.JokesViewModel
import com.vjgarcia.chucknorrisjokes.presentation.intent.*
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffect
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesEffects
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import kotlinx.android.synthetic.main.activity_jokes.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class JokesActivity : AppCompatActivity() {

    private val jokesIntents = PublishRelay.create<JokesIntent>()
    private val loadMoreClickListener = { jokesIntents.accept(LoadMoreClicked) }

    private val jokesViewModel by viewModel<JokesViewModel>()
    private val jokeAdapter by inject<JokeAdapter> { parametersOf(loadMoreClickListener) }

    private lateinit var jokesRecyclerView: RecyclerView
    private lateinit var jokesSkeleton: View
    private lateinit var jokesErrorViewStub: ViewStub
    private lateinit var jokesRefreshLayout: SwipeRefreshLayout
    private var jokesError: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)
        setUpViewModel()
        setUpView()
    }

    private fun setUpViewModel() {
        jokesViewModel.bind(jokesIntents.startWith(Start))
        jokesViewModel.state.observe(::getLifecycle, ::onJokesState)
        jokesViewModel.effects.observe(::getLifecycle, this::onJokesEffects)
    }

    private fun setUpView() {
        jokesRecyclerView = findViewById<RecyclerView>(R.id.jokes).apply {
            adapter = jokeAdapter
        }
        jokesRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.jokesSwipeRefresh).apply {
            setOnRefreshListener { jokesIntents.accept(RefreshRequested) }
        }
        jokesSkeleton = findViewById(R.id.jokesSkeleton)
        jokesErrorViewStub = findViewById(R.id.jokesErrorViewStub)
    }

    private fun onJokesState(jokesState: JokesState) {
        jokesSkeleton.isGone = !jokesState.isLoading
        jokesRecyclerView.isGone = jokesState !is JokesState.Content
        if (jokesState.isError && jokesError == null) {
            jokesError = jokesErrorViewStub.inflate().apply {
                val retryButton = findViewById<Button>(R.id.retry)
                retryButton.setOnClickListener { jokesIntents.accept(RetryClicked) }
            }
        }
        jokesError?.isGone = !jokesState.isError

        if (jokesState is JokesState.Content) {
            jokesRefreshLayout.isRefreshing = jokesState.isRefreshing
            jokeAdapter.submitList(jokesState.jokeItems)
        }
    }

    private fun onJokesEffects(jokesEffects: JokesEffects) {
        jokesEffects.values.forEach { effect -> onJokesEffect(effect)}
    }
    
    private fun onJokesEffect(jokesEffect: JokesEffect) {
        when (jokesEffect) {
            JokesEffect.DisplayRefreshError -> Log.d("Error", "here we should show a snackbar")
            JokesEffect.DisplayLoadNextError -> Log.d("Error", "here we should show a snackbar")
        }.exhaustive()
    }
}