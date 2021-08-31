package com.raproject.whattowatch

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.ui.ContentCard
import com.raproject.whattowatch.ui.theme.WhattowatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhattowatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WhattowatchTheme {
                        DefaultPreview()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ContentCard(
        content = ContentItem(
            key = 1,
            name = "Test name",
            genres = "test",
            year = "2021",
            image = Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565)
        )
    )
}
