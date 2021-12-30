package com.raproject.whattowatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer as Spacer1
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raproject.whattowatch.utils.forEachIndexedWithLastMarker
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    type: DrawerScreen,
    navigationAction: suspend (DrawerScreen) -> Unit
) {
    var scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.onPrimary)
    ) {
        Box(
            modifier = Modifier
                .height(176.dp)
                .fillMaxWidth()
                .gradientBackground(
                    colors = listOf(
                        Color(0xFFAA1888),
                        Color(0xFF660756),
                        Color(0xFF280236)
                    ),
                    angle = 135f
                )
        )
        Spacer1(Modifier.height(8.dp))

        DrawerScreen.screens.forEachIndexedWithLastMarker { index, screen, isLast ->
            val onClick = {
                scope.launch { navigationAction.invoke(screen) }
            }
            MenuItem(screen = screen, type, onClick)
        }
    }
}

@Composable
fun MenuItem(screen: DrawerScreen, currentScreen: DrawerScreen, onClick: () -> Job) {

    val backgroundColor = if (screen == currentScreen) {
        val color = MaterialTheme.colors.primary
        Color(
            red = color.red,
            blue = color.blue,
            green = color.green,
            alpha = 0.20f
        )
    } else MaterialTheme.colors.onPrimary

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(
                enabled = true,
                onClick = { onClick.invoke() }
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
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
        Text(
            text = screen.title,
            style = MaterialTheme.typography.h4,
            fontSize = 14.sp
        )
    }
}

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() // Fractional x
        val y = sin(angleRad).toFloat() // Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

@Preview
@Composable
fun DrawerPreview() {
    val navigationAction: suspend (DrawerScreen) -> Unit = { }
    Drawer(DrawerScreen.Movies, navigationAction)
}
