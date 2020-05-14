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
        val filteredJokes: List<JokeItem.Content>,
        val selectedCategories: List<String> = emptyList(),
        val categories: List<String> = emptyList(),
        private val isLoadingMore: Boolean
    ) : JokesState() {
        val jokeItems: List<JokeItem> = filteredJokes + if (isLoadingMore) JokeItem.Skeleton else JokeItem.LoadMore
    }

    sealed class Refreshing : JokesState() {
        data class Start(
            val previousJokes: List<JokeItem.Content>,
            val selectedCategories: List<String>,
            val previousFilteredJokes: List<JokeItem.Content>
        ) : Refreshing()

        data class Content(
            val jokes: List<JokeItem.Content>
        ) : Refreshing()

        val jokeItems: List<JokeItem>
            get() = when (this) {
                is Start -> previousJokes
                is Content -> jokes
            }
    }

    object Error : JokesState()

    val isLoading: Boolean
        get() = this is Loading
    val isError: Boolean
        get() = this is Error
    val isRefreshing: Boolean
        get() = this is Refreshing
    val hasContent: Boolean
        get() = this is Content || this is Refreshing
}

sealed class JokeItem {
    object Skeleton : JokeItem()
    data class Content(val id: String, val text: String, val categories: List<String>) : JokeItem()
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
    object DisplayLoadNextError : JokesEffect()
}