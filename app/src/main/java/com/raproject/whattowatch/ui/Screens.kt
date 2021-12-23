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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.R
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.utils.ContentType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
private fun ScreenBase(drawerState: DrawerState, screen: @Composable () -> Unit) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer()
        },
        content = {
            screen()
        }
    )
}

@Composable
fun ContentScreen(titlesList: List<ContentItem>, loadingVisibility: Boolean, type: ContentType) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ScreenBase(drawerState) {
        ContentView(series = titlesList, loadingVisibility, drawerState, type)
    }
}

@Composable
fun ContentView(
    series: List<ContentItem>,
    loadingVisibility: Boolean,
    drawerState: DrawerState,
    type: ContentType
) {
    val scope = rememberCoroutineScope()
    Column {
        TopBar(type.screenName, ImageVector.vectorResource(id = R.drawable.ic_menu)) {
            scope.launch {
                if (drawerState.isOpen)
                    drawerState.close()
                else drawerState.open()
            }
        }
        LoadingView(isDisplayed = loadingVisibility)
        ItemsList(ui_items = series)
    }
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
    val selected = remember { mutableStateOf(0f) }
    val rotation = animateFloatAsState(selected.value)
    var isAnimRun = false
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (!isAnimRun) {
                    isAnimRun = true
                    scope.launch {
                        val needsValue = selected.value
                        while (selected.value < (needsValue + 360)) {
                            selected.value += 7.5f
                            delay(5)
                        }
                        isAnimRun = false
                    }
                }
                // onButtonClicked()
            }, modifier = Modifier.rotate(rotation.value)) {
                Icon(imageVector = buttonIcon, contentDescription = "")
            }
            },
            backgroundColor = MaterialTheme.colors.primaryVariant,
        )
    }

    @Composable
    fun ItemsList(ui_items: List<ContentItem>) {
        LazyColumn {
            items(ui_items) {
                ContentCard(content = it)
            }
        }
    }
    