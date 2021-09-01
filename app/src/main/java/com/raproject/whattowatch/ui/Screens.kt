package com.raproject.whattowatch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@Composable
private fun ScreenBase(screen: @Composable (drawerState: DrawerState) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer()
        },
        content = {
            screen(drawerState)
        }
    )
}

@Composable
fun Movies(drawerState: DrawerState) {
    Column() {
//
    }
}
