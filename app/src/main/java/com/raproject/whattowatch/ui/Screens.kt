package com.raproject.whattowatch.ui

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.R
import com.raproject.whattowatch.models.ContentItem
import kotlinx.coroutines.launch

@Composable
private fun ScreenBase(drawerState: DrawerState, screen: @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
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
fun MoviesScreen(movies: List<ContentItem>, loadingVisibility: Boolean) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ScreenBase(drawerState) {
        MoviesView(movies = movies, loadingVisibility, drawerState)
    }
}

@Composable
fun MoviesView(
    movies: List<ContentItem>,
    loadingVisibility: Boolean,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    Column {
        TopBar("Movies", ImageVector.vectorResource(id = R.drawable.ic_menu)) {
            scope.launch {
                if (drawerState.isOpen)
                    drawerState.close()
                else drawerState.open()
            }
        }
        LoadingView(isDisplayed = loadingVisibility)
        MoviesList(movies = movies)
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
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
            Icon(imageVector = buttonIcon, contentDescription = "")
        }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun MoviesList(movies: List<ContentItem>) {
    LazyColumn {
        items(movies) {
            ContentCard(content = it)
        }
    }
}
