package com.raproject.whattowatch.utils

import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

import androidx.compose.ui.graphics.Shape


class PosterShape(private val cornerRadius: Float, private val cutPadding:Float, private val existButtonPadding:Float = cutPadding) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

      return  Outline.Generic(path = drawTicketPath(size = size, cornerRadius = cornerRadius, cutPadding= cutPadding, existButtonPadding =existButtonPadding ))
    }
}

fun drawTicketPath(size: Size, cornerRadius: Float, cutPadding:Float, existButtonPadding: Float): Path {
    return Path().apply {
        reset()
        // Top left arc

        // Top right arc

        moveTo(x = 0f, y = existButtonPadding)

        lineTo(x = 0f, y =  size.height- cutPadding)
        // Bottom right arc

        lineTo(x = cutPadding, y = size.height)


        arcTo(
            rect = Rect(
                top = size.height - cornerRadius,
                right = size.width/2f + cornerRadius,
                bottom = size.height +  cornerRadius,
                left = size.width/2f - cornerRadius,
            ),
            startAngleDegrees = -180.0f,
            sweepAngleDegrees = 180.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width-cutPadding, y = size.height)
        // Bottom left arc

        lineTo(x = size.width, y = size.height-cutPadding)

        lineTo(x = size.width, y = cutPadding)

        lineTo(x = size.width-cutPadding , y=0f)

        lineTo(x = existButtonPadding, y =0f)

        lineTo(x = 0f, y = existButtonPadding)

        close()
    }
}