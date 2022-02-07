package com.raproject.whattowatch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.forEachIndexedWithLastMarker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val iconRotation = remember { mutableStateOf(0f) }

    val rotationAngle by animateFloatAsState(iconRotation.value)

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = { Text(type.title) },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Localized description")
                        }
                    } else {
                        IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Localized description")
                        }
                    }
                },
                actions = {

                    LaunchedEffect(key1 = loadingVisibility) {
                        launch(Dispatchers.Default) {
                            while (loadingVisibility) {
                                iconRotation.value += 2.5f
                                delay(3)
                            }
                        }
                    }

                    AnimatedVisibility(visible = loadingVisibility) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Refresh),
                            contentDescription = "Loading...",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .rotate(rotationAngle)
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
                    scope.launch {
                        if (!scaffoldState.isConcealed) { scaffoldState.conceal() }
                        navigationAction.invoke(screen)
                    }
                }
                MenuItem(screen = screen, type, onClick)
            }
            Spacer(modifier = Modifier.height(8.dp))
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingView(isDisplayed: Boolean) {
    AnimatedVisibility(visible = isDisplayed) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
    }
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
