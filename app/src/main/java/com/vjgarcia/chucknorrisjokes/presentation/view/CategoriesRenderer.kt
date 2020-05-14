package com.vjgarcia.chucknorrisjokes.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxrelay2.PublishRelay
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.intent.JokesIntent

class CategoriesRenderer(
    private val categoriesChipGroup: ChipGroup,
    private val intentsRelay: PublishRelay<JokesIntent>
) {

    fun render(categories: List<String>, selectedCategories: List<String>) {
        categoriesChipGroup.removeAllViews()
        val layoutInflater = LayoutInflater.from(categoriesChipGroup.context)
        categories.forEach { category -> renderCategory(layoutInflater, category, selectedCategories) }
    }

    private fun renderCategory(layoutInflater: LayoutInflater, category: String, selectedCategories: List<String>) {
        val itemView = categoryChip(layoutInflater, categoriesChipGroup)
        itemView.isSelected = selectedCategories.contains(category)
        @SuppressLint("DefaultLocale")
        itemView.text = category.capitalize()
        itemView.setOnClickListener { intentsRelay.accept(JokesIntent.CategorySelected(category)) }
        categoriesChipGroup.addView(itemView)
    }

    private fun categoryChip(layoutInflater: LayoutInflater, chipGroup: ChipGroup): Chip =
        layoutInflater.inflate(R.layout.category_chip, chipGroup, false) as Chip
}