package com.raproject.whattowatch.ui

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun Drawer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 56.dp)
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
        DrawerScreen.screens.forEach { screen ->
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = screen.iconId),
                    contentDescription = "Icon",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Text(
                    text = screen.title,
                    style = MaterialTheme.typography.h4,
                    fontSize = 14.sp
                )
            }
        }
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
