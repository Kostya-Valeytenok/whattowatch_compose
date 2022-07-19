package com.raproject.whattowatch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.R
import com.raproject.whattowatch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenToolbar(
    screenType: DrawerScreen,
    scaffoldState: BackdropScaffoldState,
    visibility: Boolean,
    setLocalizationAction: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(screenType.title, color = TextColor.value) },
        navigationIcon = {
            NavigationIconWithAnimation(
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        actions = {
            UploadingIndicator(visibility = visibility)
            SettingsIcon(scope = scope, setLocalizationAction)
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NavigationIconWithAnimation(
    scaffoldState: BackdropScaffoldState,
    scope: CoroutineScope,
) {
    val xRotation = remember { mutableStateOf(0f) }
    val yRotation = remember { mutableStateOf(0f) }
    val iconValue = remember { mutableStateOf(Icons.Default.Menu) }

    AnimateNavigationIcon(
        iconState = scaffoldState,
        xRotation = xRotation,
        yRotation = yRotation,
        iconValue = iconValue
    )

    NavigationIcon(
        icon = iconValue.value,
        scaffoldState = scaffoldState,
        xRotation = xRotation.value,
        yRotation = yRotation.value,
        scope = scope
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NavigationIcon(
    icon: ImageVector,
    scaffoldState: BackdropScaffoldState,
    xRotation: Float,
    yRotation: Float,
    scope: CoroutineScope
) {

    IconButton(onClick = {
        scope.launch(Dispatchers.Default) {
            if (scaffoldState.isConcealed) {
                scaffoldState.reveal()
            } else scaffoldState.conceal()
        }
    }) {
        Icon(
            icon, contentDescription = "Localized description",
            modifier = Modifier.graphicsLayer(
                rotationX = xRotation,
                rotationY = yRotation,
            ),
            tint = TextColor.value
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun UploadingIndicator(visibility: Boolean) {
    val rotation = remember { mutableStateOf(0f) }

    LoaderAnimation(
        isVisible =
        visibility,
        state = rotation,
        values = listOf(0f, 90f, 180f, 270f, 360f),
        animationSpec = fullTimeAnimationSpec
    )

    AnimatedVisibility(visible = visibility) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.Refresh),
            contentDescription = "Loading...",
            modifier = Modifier
                .padding(end = 4.dp)
                .rotate(rotation.value),
            tint = TextColor.value
        )
    }
}

@Composable
private fun SettingsIcon(scope: CoroutineScope, setLocalizationAction: suspend () -> Unit) {
    IconButton(
        onClick = {
            scope.launch {
                setLocalizationAction.invoke()
            }
        },
    ) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.ic_translate),
            contentDescription = "Settings",
            tint = TextColor.value
        )
    }
}
