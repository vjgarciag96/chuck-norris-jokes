package com.vjgarcia.chucknorrisjokes.presentation.view

import android.graphics.Color
import android.graphics.Typeface
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge

@LayoutSpec
object JokeElementSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop text: String): Component {
        val textComponent = Text.create(c)
            .text(text)
            .marginDip(YogaEdge.TOP, 16f)
            .marginDip(YogaEdge.BOTTOM, 8f)
            .marginDip(YogaEdge.HORIZONTAL, 8f)
            .typeface(Typeface.SANS_SERIF)
            .textColor(Color.BLACK)
            .build()

        return Column.create(c)
            .child(textComponent)
            .build()
    }
}