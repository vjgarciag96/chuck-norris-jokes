package com.vjgarcia.chucknorrisjokes.presentation.view

import android.view.View
import com.facebook.litho.ClickEvent
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import com.vjgarcia.chucknorrisjokes.presentation.model.Joke
import com.vjgarcia.chucknorrisjokes.presentation.model.JokesState

@GroupSectionSpec
object JokesListSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(
        sc: SectionContext,
        @Prop state: JokesState
    ): Children {
        val childrenBuilder = Children.create()

        val jokeElementDiffSection = DataDiffSection.create<Joke>(sc)
            .data(state.jokes)
            .renderEventHandler(JokesListSection.onRender(sc))
        childrenBuilder.child(jokeElementDiffSection)

        if (state.isLoading) {
            val loadingComp = SingleComponentSection.create(sc).component(LoadingJokesElement.create(sc)).build()
            childrenBuilder.child(loadingComp)
        }

        if (state.loadMoreEnabled) {
            val loadMoreComp = SingleComponentSection.create(sc).component(
                LoadMoreElement
                    .create(sc)
                    .clickHandler(JokesListSection.onLoadNextClicked(sc))
            ).build()
            childrenBuilder.child(loadMoreComp)
        }

        return childrenBuilder.build()
    }

    @OnEvent(RenderEvent::class)
    fun onRender(sc: SectionContext, @FromEvent model: Joke): RenderInfo {
        return ComponentRenderInfo.create().component(
            JokeElement.create(sc)
                .text(model.text)
        ).build()
    }

    @OnEvent(ClickEvent::class)
    fun onLoadNextClicked(sc: SectionContext, @Prop onLoadNextClicked: () -> Unit) {
        onLoadNextClicked()
    }
}