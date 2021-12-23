package com.raproject.whattowatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import kotlinx.coroutines.launch

@Composable
fun Drawer() {
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

            MenuItem(screen = screen)
            if (!isLast)
                Divider(Modifier.height(1.dp).padding(start = 8.dp))
        }
    }
}

@Composable
fun MenuItem(screen: DrawerScreen) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.onPrimary)
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
    Drawer()
}

@Composable
fun DrawerAPHA() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer()
        },
        content = {
            Column {
                Text("Text in Bodycontext")
                Button(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                ) {
                    Text("Click to open")
                }
            }
        },
    )
}

@Preview
@Composable
fun DrawerAPHAPreview() {
    DrawerAPHA()
}
