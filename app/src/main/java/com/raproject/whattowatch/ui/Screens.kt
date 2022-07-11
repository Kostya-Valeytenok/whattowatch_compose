package com.raproject.whattowatch.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.raproject.whattowatch.models.ContentItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentScreen(
    titlesList: List<ContentItem>,
    loadingVisibility: Boolean,
    type: DrawerScreen,
    navigationAction: suspend (DrawerScreen) -> Unit
) {

    ScreenBase(type, loadingVisibility, navigationAction) {
        ContentView(series = titlesList, loadingVisibility)
    }
}

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

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            MainScreenToolbar(
                screenType = type,
                scaffoldState = scaffoldState,
                visibility = loadingVisibility
            )
        },
        backLayerContent = {
            MenuView(
                currentScreenType = type,
                scaffoldState = scaffoldState,
                navigationAction = navigationAction
            )
        },
        frontLayerContent = { screen() },
        backLayerBackgroundColor =  MaterialTheme.colorScheme.tertiary
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentView(
    series: List<ContentItem>,
    loadingVisibility: Boolean,
) {
    Column {
        ItemsList(ui_items = series, loadingVisibility)
    }
}
