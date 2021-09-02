package com.raproject.whattowatch.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {
//
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
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
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
            }
            Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                Text(
                    text = content.name, modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(text = content.year, Modifier.padding(top = 16.dp).fillMaxWidth())
                Text(text = content.genres, Modifier.padding(top = 2.dp).fillMaxWidth())
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
