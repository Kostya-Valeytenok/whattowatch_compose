package com.raproject.whattowatch.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoaderAnimation(
    isVisible: Boolean,
    state: MutableState<Float>,
    values: List<Float>,
    animationSpec: AnimationSpec<Float> = spring(),
) {
    val groups by rememberUpdatedState(newValue = values.zipWithNext())
    LaunchedEffect(key1 = isVisible) {
        withContext(Dispatchers.Default) {
            if (isVisible) {
                animate(
                    initialValue = 0f,
                    targetValue = groups.size.toFloat(),
                    animationSpec = animationSpec,
                ) { frame, g ->

                    val integerPart = frame.toInt()
                    val index = frame.toInt()
                    if (index <= groups.size - 1) {
                        val (initialValue, finalValue) = groups[index]
                        val decimalPart = frame - integerPart
                        state.value = initialValue + (finalValue - initialValue) * decimalPart
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimateNavigationIcon(
    iconValue: MutableState<ImageVector>,
    iconState: BackdropScaffoldState,
    xRotation: MutableState<Float>,
    yRotation: MutableState<Float>
) {

    val xGroup by rememberUpdatedState(newValue = xRotationValue.zipWithNext())
    val yGroup by rememberUpdatedState(newValue = yRotationValue.zipWithNext())

    LaunchedEffect(key1 = iconState.targetValue, block = {
        withContext(Dispatchers.Default) {
            var isChange = false
            animate(
                initialValue = 0f,
                targetValue = xGroup.size.toFloat(),
                animationSpec = animationSpec,
            ) { frame, _ ->
                // Get which group is being evaluated
                val integerPart = frame.toInt()
                val index = frame.toInt()
                if (index < xGroup.size) {
                    launch {
                        if (!isChange == index >= 2) {
                            isChange = true
                            if (iconState.targetValue == BackdropValue.Concealed) {
                                iconValue.value = Icons.Default.Menu
                            } else {
                                iconValue.value = Icons.Default.Close
                            }
                        }
                    }
                    val (xInitialValue, xFinalValue) = xGroup[index]
                    val (yInitialValue, yFinalValue) = yGroup[index]

                    // Get the current "position" from the group animation
                    val decimalPart = frame - integerPart
                    // Calculate the progress between the initial and final value
                    launch(Dispatchers.Default) {
                        xRotation.value =
                            xInitialValue + (xFinalValue - xInitialValue) * decimalPart
                    }
                    launch(Dispatchers.Default) {
                        yRotation.value =
                            yInitialValue + (yFinalValue - yInitialValue) * decimalPart
                    }
                }
            }
        }
    })
}

