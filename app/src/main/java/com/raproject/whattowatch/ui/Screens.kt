package com.raproject.whattowatch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.forEachIndexedWithLastMarker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val animationSpec = repeatable<Float>(
    iterations = 1,
    animation = tween(
        durationMillis = 700,
        easing = LinearEasing,
    ),
    repeatMode = RepeatMode.Reverse
)

val xRotationValue = listOf(0f, 45f, 135f, 210f, 180f)
val yRotationValue = listOf(0f, 0f, 45f, 135f, 180f)

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenBase(
    type: DrawerScreen,
    loadingVisibility: Boolean,
    navigationAction: suspend (DrawerScreen) -> Unit,
    screen: @Composable () -> Unit
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val scope = rememberCoroutineScope()

    val iconValue = remember {
        mutableStateOf(Icons.Default.Menu)
    }

    val loaderSpec = infiniteRepeatable<Float>(
        animation = tween(
            durationMillis = 700,
            easing = LinearEasing,
        ),
    )

    val animationSpec =  infiniteRepeatable<Float>(
        animation = tween(
            durationMillis = 700,
            easing = LinearEasing,
        ),
    )

    val rotation = remember { mutableStateOf(0f) }

    LoaderAnimation(
        isVisible =
        loadingVisibility,
        state = rotation,
        values = listOf(0f, 90f, 180f, 270f, 360f),
        animationSpec = animationSpec
    )

    val xRotation = remember { mutableStateOf(0f) }
    val yRotation = remember { mutableStateOf(0f) }

    IconAnimation(
        iconState = scaffoldState,
        xRotation = xRotation,
        yRotation = yRotation,
        iconValue = iconValue
    )

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = { Text(type.title) },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch(Dispatchers.Default) {
                            if (scaffoldState.isConcealed) {
                                scaffoldState.reveal()
                            } else scaffoldState.conceal()
                        }
                    }) {
                        Icon(
                            iconValue.value, contentDescription = "Localized description",
                            modifier = Modifier.graphicsLayer(
                                rotationX = xRotation.value,
                                rotationY = yRotation.value,
                            )
                        )
                    }
                },
                actions = {

                    AnimatedVisibility(visible = loadingVisibility) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Refresh),
                            contentDescription = "Loading...",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .rotate(rotation.value)
                        )
                    }

                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            DrawerScreen.screens.forEachIndexedWithLastMarker { index, screen, isLast ->
                val onClick = {
                    scope.launch(Dispatchers.Default) {
                        if (!scaffoldState.isConcealed) {
                            scaffoldState.conceal()
                        }
                        delay(100)
                        navigationAction.invoke(screen)
                    }
                }
                MenuItem(screen = screen, type, onClick)
                Spacer(modifier = Modifier.height(2.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
        },
        frontLayerContent = {
            screen()
        },
        backLayerBackgroundColor = MaterialTheme.colors.primaryVariant
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentScreen(
    titlesList: List<ContentItem>,
    loadingVisibility: Boolean,
    type: DrawerScreen,
    navigationAction: suspend (DrawerScreen) -> Unit
) {

    ScreenBase(type, loadingVisibility, navigationAction) {
        ContentView(series = titlesList)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentView(
    series: List<ContentItem>,
) {
    Column {
        ItemsList(ui_items = series)
    }
}

suspend fun DrawerState.inverse() {
    if (isOpen) close()
    else open()
}

@Composable
fun LoaderAnimation(
    isVisible: Boolean,
    state: MutableState<Float>,
    values: List<Float>,
    animationSpec: AnimationSpec<Float> = spring(),
) {
    val groups by rememberUpdatedState(newValue = values.zipWithNext())
    LaunchedEffect(key1 = isVisible) {
        animate(
            initialValue = 0f,
            targetValue = groups.size.toFloat(),
            animationSpec = animationSpec,
        ) { frame, _ ->

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconAnimation(
    iconValue: MutableState<ImageVector>,
    iconState: BackdropScaffoldState,
    xRotation: MutableState<Float>,
    yRotation: MutableState<Float>
) {

    val xGroup by rememberUpdatedState(newValue = xRotationValue.zipWithNext())
    val yGroup by rememberUpdatedState(newValue = yRotationValue.zipWithNext())

    LaunchedEffect(key1 = iconState.targetValue, block = {
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
                launch(Dispatchers.Default) {
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
                    xRotation.value = xInitialValue + (xFinalValue - xInitialValue) * decimalPart
                }
                launch(Dispatchers.Default) {
                    yRotation.value = yInitialValue + (yFinalValue - yInitialValue) * decimalPart
                }
            }
        }
    })
}

@Composable
fun ItemsList(ui_items: List<ContentItem>) {
    LazyColumn(
        contentPadding =
        PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        items(ui_items) {
            ContentCard(content = it)
        }
    }
}
