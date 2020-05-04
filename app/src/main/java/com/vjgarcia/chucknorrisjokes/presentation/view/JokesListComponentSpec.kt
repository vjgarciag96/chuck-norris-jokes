package com.vjgarcia.chucknorrisjokes.presentation.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.ListRecyclerConfiguration
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.vjgarcia.chucknorrisjokes.R
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

@LayoutSpec
object JokesListComponentSpec {

    @OnCreateLayout
    fun createLayout(
        c: ComponentContext,
        @Prop state: JokesState,
        @Prop onLoadNextClicked: () -> Unit
    ): Component {
        val recyclerViewConfiguration = ListRecyclerConfiguration.create()
            .orientation(LinearLayoutManager.HORIZONTAL)
            .build()

        return RecyclerCollectionComponent.create(c)
            .recyclerConfiguration(recyclerViewConfiguration)
            .disablePTR(true)
            .paddingRes(YogaEdge.START, R.dimen.generic_padding)
            .paddingRes(YogaEdge.END, R.dimen.generic_padding)
            .clipToPadding(false)
            .alignSelf(YogaAlign.CENTER)
            .section(
                JokesListSection
                    .create(SectionContext(c))
                    .state(state)
                    .onLoadNextClicked { onLoadNextClicked() }
            ).build()
    }
}