package com.raproject.whattowatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.layout.Spacer as Spacer1
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
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
import com.raproject.whattowatch.ui.theme.White
import kotlinx.coroutines.Job

val shape = RoundedCornerShape(
    bottomEnd = 48.dp,
    topEnd = 48.dp,
)

@Composable
fun MenuItem(screen: DrawerScreen, currentScreen: DrawerScreen, onClick: () -> Job) {

    val scope = rememberCoroutineScope()
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
        Spacer1(Modifier.height(8.dp))
        MenuItemContent(screen = screen)
        Spacer1(Modifier.height(8.dp))
    }
}

@Composable
fun MenuItemContent(screen: DrawerScreen) {
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
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
        Text(
            text = screen.title,
            style = MaterialTheme.typography.h4,
            fontSize = 14.sp
        )
    }
}
