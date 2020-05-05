package com.vjgarcia.chucknorrisjokes.presentation.view

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

class JokeItemViewHolder(
    itemView: View,
    private val loadMoreClickListener: LoadMoreClickListener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(jokeItem: JokeItem) {
        when (jokeItem) {
            JokeItem.Skeleton -> Unit
            is JokeItem.Content -> bindContent(itemView, jokeItem)
            JokeItem.LoadMore -> bindLoadMore(itemView, loadMoreClickListener)
        }
    }

    private fun bindContent(itemView: View, content: JokeItem.Content) {
        val jokeTextView = itemView.findViewById<TextView>(R.id.text)
        jokeTextView.text = content.text
    }

    private fun bindLoadMore(itemView: View, loadMoreClickListener: LoadMoreClickListener) {
        val loadMoreCard = itemView.findViewById<CardView>(R.id.card)
        loadMoreCard.setOnClickListener { loadMoreClickListener.onClick() }
    }
}