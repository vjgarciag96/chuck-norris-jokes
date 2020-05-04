package com.vjgarcia.chucknorrisjokes.presentation.view

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

@LayoutSpec
object JokesRootComponentSpec {

    @OnCreateLayout
    fun createLayout(
        c: ComponentContext,
        @Prop state: JokesState,
        @Prop onLoadNextClicked: () -> Unit
    ): Component = RecyclerCollectionComponent.create(c)
        .disablePTR(true)
        .section(
            JokesListSection
                .create(SectionContext(c))
                .state(state)
                .onLoadNextClicked { onLoadNextClicked() }
                .build()
        )
        .build()
}