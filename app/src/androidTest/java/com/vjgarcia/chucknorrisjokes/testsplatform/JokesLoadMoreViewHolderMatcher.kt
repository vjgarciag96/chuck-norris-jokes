package com.vjgarcia.chucknorrisjokes.testsplatform

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.view.JokeItemViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher

class JokesLoadMoreViewHolderMatcher private constructor() : BoundedMatcher<RecyclerView.ViewHolder, JokeItemViewHolder>(JokeItemViewHolder::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("Jokes list load more button mathcer")
    }

    override fun matchesSafely(item: JokeItemViewHolder): Boolean {
        val loadMoreButton = item.itemView.findViewById<Button>(R.id.loadMore)
        return loadMoreButton != null
    }

    companion object {
        fun jokesLoadMore(): Matcher<RecyclerView.ViewHolder> = JokesLoadMoreViewHolderMatcher()
    }
}