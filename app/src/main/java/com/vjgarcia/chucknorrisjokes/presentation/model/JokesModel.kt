package com.vjgarcia.chucknorrisjokes.presentation.model

data class JokesModel(
    val state: JokesState,
    val effects: JokesEffects
) {
    companion object {
        fun from(state: JokesState): JokesModel = JokesModel(state, JokesEffects.None)
        fun from(state: JokesState, effects: JokesEffects): JokesModel = JokesModel(state, effects)
    }
}

sealed class JokesState {
    object Loading : JokesState()
    data class Content(
        val jokes: List<JokeItem.Content>,
        val isRefreshing: Boolean = false,
        private val isLoadingMore: Boolean
    ) : JokesState() {
        val jokeItems: List<JokeItem>
            get() = jokes + if (isLoadingMore) JokeItem.Skeleton else JokeItem.LoadMore
    }
    object Error : JokesState()

    val isLoading: Boolean
        get() = this is Loading
    val isError: Boolean
        get() = this is Error
}

sealed class JokeItem {
    object Skeleton : JokeItem()
    data class Content(val id: String, val text: String) : JokeItem()
    object LoadMore : JokeItem()
}

sealed class JokesEffects {
    object None : JokesEffects()
    data class Some(val effects: List<JokesEffect>) : JokesEffects()

    val values: List<JokesEffect>
        get() = if (this is Some) effects else emptyList()

    companion object {
        fun from(vararg effect: JokesEffect): JokesEffects = Some(effect.toList())
    }
}

sealed class JokesEffect {
    object DisplayRefreshError : JokesEffect()
    object DisplayLoadNextError: JokesEffect()
}