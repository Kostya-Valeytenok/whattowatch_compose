package com.raproject.whattowatch.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem

@Composable
fun ContentCard(content: ContentItem) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable(enabled = true, onClick = {}),
        shape = RoundedCornerShape(8.dp)
    ) {
//
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(end = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(115.dp)
            ) {
                Image(
                    contentDescription = "Poster",
                    bitmap = content.image.asImageBitmap(),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
//            
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 16.dp)
            ) {
                Text(
                    text = content.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = content.year,
                    Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = content.genres,
                    Modifier
                        .padding(top = 2.dp)
                        .fillMaxWidth()
                )
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
