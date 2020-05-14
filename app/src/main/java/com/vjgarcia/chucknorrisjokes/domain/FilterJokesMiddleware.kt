package com.vjgarcia.chucknorrisjokes.domain

import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class FilterJokesMiddleware {

    fun bind(actions: Observable<JokesAction>, states: Observable<JokesState>): Observable<JokesActionResult> =
        actions.ofType(JokesAction.UpdateCategoryFilter::class.java)
            .withLatestFrom(states, BiFunction { action: JokesAction.UpdateCategoryFilter, state: JokesState -> action to state })
            .subscribeOn(Schedulers.computation())
            .filter { (_, state) -> state is JokesState.Content }
            .map { (action, currentState) ->
                val contentState = currentState as JokesState.Content
                val currentSelectedCategories = contentState.selectedCategories
                val isSelection = !currentSelectedCategories.contains(action.category)
                val selectedCategories = if (isSelection) {
                    currentSelectedCategories + action.category
                } else {
                    currentSelectedCategories - action.category
                }
                return@map FilterJokesResult(
                    filteredJokes = contentState.jokes.filterByCategories(selectedCategories),
                    selectedCategories = selectedCategories
                )
            }

    private fun List<JokeItem.Content>.filterByCategories(categories: List<String>) =
        if (categories.isEmpty()) this else filter { joke -> joke.categories.any { categories.contains(it) }
    }
}