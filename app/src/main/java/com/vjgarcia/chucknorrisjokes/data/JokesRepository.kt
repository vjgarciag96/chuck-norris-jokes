package com.vjgarcia.chucknorrisjokes.data

import com.vjgarcia.chucknorrisjokes.mappers.toJoke
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class JokesRepository(
    private val chuckNorrisApiService: ChuckNorrisApiService,
    private val chuckNorrisStorage: ChuckNorrisStorage,
    private val categorySelector: CategorySelector
) {

    fun randomStream(jokesCount: Int): Observable<Joke> = categories().toObservable().flatMap { categories ->
        randomJokes(jokesCount, categories)
    }

    private fun categories(): Single<List<String>> = chuckNorrisApiService.categories()
        .doOnSuccess { categories -> chuckNorrisStorage.categories = categories }
        .onErrorResumeNext(localCategories())

    private fun localCategories(): Single<List<String>> = Single.create { emitter -> emitter.onSuccess(chuckNorrisStorage.categories) }

    private fun randomJokes(
        jokesCount: Int,
        categories: List<String>
    ): Observable<Joke> = Observable.range(0, jokesCount).flatMap {
        randomByCategory(categorySelector.select(categories))
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    private fun randomByCategory(category: String): Single<Joke> = chuckNorrisApiService.randomByCategory(category).map { it.toJoke() }
}