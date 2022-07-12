package com.raproject.whattowatch.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.raproject.whattowatch.models.ContentItem

@Composable
fun ItemsList(
    ui_items: List<ContentItem>,
    loadingVisibility: Boolean,
    openAboutContentScreenAction: (String) -> Unit
) {

    val rotationAngle by animateFloatAsState(
        targetValue = if (!loadingVisibility) 1f else 0f,
        animationSpec = tween(durationMillis = if (!loadingVisibility) 500 else 150),
    )
    LazyColumn(
        contentPadding =
        PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        modifier = Modifier.alpha(rotationAngle)
    ) {
        itemsIndexed(ui_items) { index, item ->
            ContentCard(content = item, ratingNumber = index + 1,openAboutContentScreenAction= openAboutContentScreenAction)
        }
    }
}