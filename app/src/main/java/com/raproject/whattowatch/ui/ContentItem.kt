package com.raproject.whattowatch.ui.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem

@Composable
fun ContentCard(content: ContentItem) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(16.dp)
    ) {
//
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            ) {
                Image(
                    contentDescription = "Poster",
                    bitmap = content.image.asImageBitmap(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = content.name)
                Text(text = content.year, Modifier.padding(top = 8.dp))
                Text(text = content.name, Modifier.padding(top = 8.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CardPreview() {
    ContentCard(
        content = ContentItem(key = 1, image = Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565), name = "Test name", genres = "test", year = "2021")
    )
}
