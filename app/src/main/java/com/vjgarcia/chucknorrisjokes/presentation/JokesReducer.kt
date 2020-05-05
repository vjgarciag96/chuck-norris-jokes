package com.vjgarcia.chucknorrisjokes.presentation

import com.vjgarcia.chucknorrisjokes.domain.JokesActionResult
import com.vjgarcia.chucknorrisjokes.domain.LoadInitialResult
import com.vjgarcia.chucknorrisjokes.domain.LoadNextResult
import com.vjgarcia.chucknorrisjokes.mappers.toJokeItemContent
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

class JokesReducer {

    fun reduce(previousState: JokesState, actionResult: JokesActionResult): JokesState =
        when {
            actionResult is LoadInitialResult.Loading -> JokesState.Loading
            actionResult is LoadInitialResult.Error -> JokesState.Error
            previousState is JokesState.Loading && actionResult is LoadInitialResult.Success ->
                JokesState.Content(actionResult.initialJokes.map { it.toJokeItemContent() } + JokeItem.LoadMore)
            previousState is JokesState.Content && actionResult is LoadInitialResult.Success ->
                JokesState.Content(previousState.jokeItems + actionResult.initialJokes.map { it.toJokeItemContent() } + JokeItem.LoadMore)
            previousState is JokesState.Content && actionResult is LoadNextResult.Loading ->
                previousState.copy(jokeItems = previousState.jokeItems.dropLast(1) + JokeItem.Skeleton)
            previousState is JokesState.Content && actionResult is LoadNextResult.Error ->
                previousState.copy(jokeItems = previousState.jokeItems.dropLast(1) + JokeItem.LoadMore)
            previousState is JokesState.Content && actionResult is LoadNextResult.Success ->
                previousState.copy(jokeItems = previousState.jokeItems.dropLast(1) + actionResult.nextJoke.toJokeItemContent() + JokeItem.LoadMore)
            else -> error("invalid combination of previousState = $previousState and actionResult = $actionResult")
        }
}