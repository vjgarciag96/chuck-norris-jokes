package com.vjgarcia.chucknorrisjokes.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem
import java.lang.IllegalArgumentException

class JokeAdapter(
    jokeItemDiffCallback: JokeItemDiffCallback,
    private val loadMoreClickListener: LoadMoreClickListener
) : ListAdapter<JokeItem, JokeItemViewHolder>(jokeItemDiffCallback) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        JokeItem.Skeleton -> VIEW_TYPE_SKELETON
        is JokeItem.Content -> VIEW_TYPE_CONTENT
        JokeItem.LoadMore -> VIEW_TYPE_LOAD_MORE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = when (viewType) {
            VIEW_TYPE_SKELETON -> layoutInflater.inflate(R.layout.item_joke_skeleton, parent, false)
            VIEW_TYPE_CONTENT -> layoutInflater.inflate(R.layout.item_joke_content, parent, false)
            VIEW_TYPE_LOAD_MORE -> layoutInflater.inflate(R.layout.item_joke_load_more, parent, false)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
        return JokeItemViewHolder(itemView, loadMoreClickListener)
    }

    override fun onBindViewHolder(holder: JokeItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        const val VIEW_TYPE_SKELETON = 0
        const val VIEW_TYPE_CONTENT = 1
        const val VIEW_TYPE_LOAD_MORE = 2
    }
}