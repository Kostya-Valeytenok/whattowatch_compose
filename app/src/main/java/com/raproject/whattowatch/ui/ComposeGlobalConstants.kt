package com.raproject.whattowatch.ui

import androidx.compose.animation.core.*

val animationSpec = repeatable<Float>(
    iterations = 1,
    animation = tween(
        durationMillis = 700,
        easing = LinearEasing,
    ),
    repeatMode = RepeatMode.Reverse
)

val fullTimeAnimationSpec = infiniteRepeatable<Float>(
    animation = tween(
        durationMillis = 700,
        easing = LinearEasing,
    ),
)

val xRotationValue = listOf(0f, 45f, 135f, 210f, 180f)
val yRotationValue = listOf(0f, 0f, 45f, 135f, 180f)