package com.vjgarcia.chucknorrisjokes.presentation.view

import android.graphics.Color
import android.graphics.Typeface
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Card
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaPositionType
import com.vjgarcia.chucknorrisjokes.R

@LayoutSpec
object JokeElementSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop text: String): Component {
        val textComponent = Text.create(c)
            .text(text)
            .alignSelf(YogaAlign.CENTER)
            .positionType(YogaPositionType.RELATIVE)
            .paddingRes(YogaEdge.ALL, R.dimen.generic_padding)
            .typeface(Typeface.SANS_SERIF)
            .textColor(Color.BLACK)
            .build()

        val column = Column.create(c)
            .alignSelf(YogaAlign.FLEX_END)
            .child(textComponent)
            .positionType(YogaPositionType.ABSOLUTE)
            .build()

        return Card.create(c)
            .cornerRadiusRes(R.dimen.joke_card_corner_radius)
            .widthRes(R.dimen.joke_card_width)
            .heightRes(R.dimen.joke_card_height)
            .marginRes(YogaEdge.BOTTOM, R.dimen.joke_card_margin)
            .marginRes(YogaEdge.START, R.dimen.joke_card_margin)
            .marginRes(YogaEdge.END, R.dimen.joke_card_margin)
            .content(column)
            .build()
    }
}