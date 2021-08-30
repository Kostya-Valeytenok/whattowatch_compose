package com.raproject.whattowatch

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import com.raproject.whattowatch.ui.ui.ContentCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhattowatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WhattowatchTheme {
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        WhattowatchTheme {
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
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
