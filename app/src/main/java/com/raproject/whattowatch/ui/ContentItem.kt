package com.raproject.whattowatch.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem

@Composable
fun ContentCard(content: ContentItem) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
//
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    contentDescription = "Poster",
                    bitmap = content.image.asImageBitmap(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                Text(
                    text = content.name, modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
                Text(text = content.year, Modifier.padding(top = 16.dp).fillMaxWidth())
                Text(text = content.name, Modifier.padding(top = 2.dp).fillMaxWidth())
            }
        }
    }
}
@Preview
@Composable
fun CardPreview() {
    ContentCard(
        content = ContentItem(key = 1, image = Bitmap.createBitmap(5, 5, Bitmap.Config.RGB_565), name = "Test name", genres = "test", year = "2021")
    )
}
