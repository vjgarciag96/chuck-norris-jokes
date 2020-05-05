package com.vjgarcia.chucknorrisjokes.presentation.view

import androidx.recyclerview.widget.DiffUtil
import com.vjgarcia.chucknorrisjokes.presentation.model.JokeItem

class JokeItemDiffCallback : DiffUtil.ItemCallback<JokeItem>() {

    override fun areItemsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean = when {
        oldItem is JokeItem.Content && newItem is JokeItem.Content -> oldItem.id == newItem.id
        else -> false
    }

    override fun areContentsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean = oldItem == newItem
}