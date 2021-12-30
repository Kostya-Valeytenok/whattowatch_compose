package com.raproject.whattowatch.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.R
import com.raproject.whattowatch.models.ContentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
private fun ScreenBase(
    drawerState: DrawerState,
    type: DrawerScreen,
    navigationAction: suspend (DrawerScreen) -> Unit,
    screen: @Composable () -> Unit
) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(type, navigationAction)
        },
        content = {
            screen()
        }
    )
}

@Composable
fun ContentScreen(
    titlesList: List<ContentItem>,
    loadingVisibility: Boolean,
    type: DrawerScreen,
    navigationAction:  (DrawerScreen) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navigationActionWithDrawerState: suspend (DrawerScreen) -> Unit = {
        drawerState.inverse()
        navigationAction.invoke(it)

    }
    ScreenBase(drawerState, type, navigationActionWithDrawerState) {
        ContentView(series = titlesList, loadingVisibility, drawerState, type)
    }
}

@Composable
fun ContentView(
    series: List<ContentItem>,
    loadingVisibility: Boolean,
    drawerState: DrawerState,
    type: DrawerScreen
) {
    val scope = rememberCoroutineScope()
    Column {
        TopBar(type.title, ImageVector.vectorResource(id = R.drawable.ic_menu)) {
            scope.launch { drawerState.inverse() }
        }
        LoadingView(isDisplayed = loadingVisibility)
        ItemsList(ui_items = series)
    }
}

suspend fun DrawerState.inverse() {
    if (isOpen) close()
    else open()
}

@Composable
fun LoadingView(isDisplayed: Boolean) {
    if (isDisplayed) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    val scope = rememberCoroutineScope()
    val iconRotation = remember { mutableStateOf(0f) }
    val rotation = animateFloatAsState(iconRotation.value)
    var isAnimRun = false
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onButtonClicked()
                if (!isAnimRun) {
                    isAnimRun = true
                    scope.launch(Dispatchers.Default) {
                        startAnim(iconRotation)
                        isAnimRun = false
                    }
                }
            }, modifier = Modifier.rotate(rotation.value)) {
                Icon(imageVector = buttonIcon, contentDescription = "")
            }
            },
            backgroundColor = MaterialTheme.colors.primaryVariant,
        )
    }

    suspend fun startAnim(iconRotation: MutableState<Float>) {
        val needsValue = iconRotation.value
        while (iconRotation.value < (needsValue + 360)) {
            iconRotation.value += 7.5f
            delay(5)
        }
    }

    @Composable
    fun ItemsList(ui_items: List<ContentItem>) {
        LazyColumn {
            items(ui_items) {
                ContentCard(content = it)
            }
        }
    }
    