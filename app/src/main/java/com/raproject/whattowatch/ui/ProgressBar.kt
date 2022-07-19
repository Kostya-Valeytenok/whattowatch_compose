package com.raproject.whattowatch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.BoxProgressBar(
    visibilityState: MutableTransitionState<Boolean> = MutableTransitionState(true)
) {
    AnimatedVisibility(
        visibleState = visibilityState,
        modifier = Modifier.align(Alignment.Center)
    ) {

        CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)

    }
}