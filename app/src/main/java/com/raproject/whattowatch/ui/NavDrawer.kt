package com.raproject.whattowatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raproject.whattowatch.ui.theme.TextColor
import com.raproject.whattowatch.ui.theme.White
import com.raproject.whattowatch.utils.forEachIndexedWithLastMarker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val shape = RoundedCornerShape(
    bottomEnd = 48.dp,
    topEnd = 48.dp,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuView(
    currentScreenType: DrawerScreen,
    scaffoldState: BackdropScaffoldState,
    navigationAction: suspend (DrawerScreen) -> Unit
) {
    val scope = rememberCoroutineScope()

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
        MenuItem(screen = screen, currentScreenType, onClick)

        Spacer(modifier = Modifier.height(2.dp))
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
private fun MenuItem(screen: DrawerScreen, currentScreen: DrawerScreen, onClick: () -> Job) {

    var modifier = Modifier
        .padding(end = 8.dp)
        .wrapContentHeight()
        .fillMaxWidth()

    modifier = if (screen == currentScreen) {
        modifier.background(White.copy(alpha = 0.20f), shape = shape)
    } else modifier.background(Color.Transparent, shape = shape)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
            .clickable(
                enabled = true,
                onClick = { onClick.invoke() },
            ),
    ) {
        Spacer(Modifier.height(8.dp))
        MenuItemContent(screen = screen)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun MenuItemContent(screen: DrawerScreen) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = screen.iconId),
            contentDescription = "Icon",
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            colorFilter = ColorFilter.tint(TextColor.value)
        )
        Text(
            text = screen.title,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 14.sp,
            color = TextColor.value
        )
    }
}
