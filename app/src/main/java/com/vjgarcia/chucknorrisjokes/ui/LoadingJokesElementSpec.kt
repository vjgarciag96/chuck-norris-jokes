package com.vjgarcia.chucknorrisjokes.ui

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Progress
import com.facebook.yoga.YogaAlign

@LayoutSpec
object LoadingJokesElementSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext): Component =
        Column.create(c).child(
            Progress.create(c)
                .widthDip(40f)
                .heightDip(40f)
                .alignSelf(YogaAlign.CENTER)
                .build()
        ).build()
}