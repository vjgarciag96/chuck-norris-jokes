package com.vjgarcia.chucknorrisjokes.testsplatform

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher

class RecyclerViewItemCountAssertion private constructor(
    private val matcher: Matcher<Int>
) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        assertTrue(matcher.matches(itemCount))
    }

    companion object {
        fun withItemCount(count: Int): RecyclerViewItemCountAssertion = withItemCount(`is`(count))
        private fun withItemCount(countMatcher: Matcher<Int>): RecyclerViewItemCountAssertion = RecyclerViewItemCountAssertion(countMatcher)
    }
}