package com.vjgarcia.chucknorrisjokes.testsplatform

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem
import com.vjgarcia.chucknorrisjokes.presentation.view.JokeAdapter
import junit.framework.TestCase.assertEquals

class JokeTextAssertion private constructor(
    private val textToBeMatched: String,
    private val position: Int
) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter as? JokeAdapter ?: error("applying view assertion to the wrong recycler view")
        val item = adapter.currentList.getOrNull(position) ?: error("item at $position doesn't exist")
        val joke = item as? JokeItem.Content ?: error("trying to assert on an incorrect item type")
        assertEquals(textToBeMatched, joke.text)
    }

    companion object {
        fun withJokeTextAtPosition(jokeText: String, position: Int): JokeTextAssertion = JokeTextAssertion(jokeText, position)
    }
}