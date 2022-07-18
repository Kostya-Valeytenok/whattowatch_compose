package com.raproject.whattowatch.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raproject.whattowatch.R
import com.raproject.whattowatch.ui.theme.Pink80
import com.raproject.whattowatch.ui.theme.WhattowatchTheme

@Composable
fun RowScope.BaseRaringView(color: Color, @DrawableRes iconRes: Int, rating: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = color,
        modifier = Modifier
            .height(100.dp)
            .weight(0.5f, fill = true)
            .padding(4.dp)
    ) {

        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = "content description",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(fraction = 0.5f)
                    .align(Alignment.CenterVertically),
                alignment = Alignment.TopStart
            )

            Box() {
                Text(
                    text = rating, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun RowScope.KinopoiskRating(rating: String) {
    BaseRaringView(color = Color.Black, iconRes = R.drawable.ic_kinopoisk, rating = rating)
}

@Composable
fun RowScope.DevRating(rating: String) {
    BaseRaringView(color = Pink80, iconRes = R.drawable.ic_company, rating = rating)
}

@Composable
fun RatingView(kinopoiskRating: String?, devRating: String?) {
    if (kinopoiskRating == null && devRating == null) return
    val isLastNull = kinopoiskRating == null

    Row(modifier = Modifier.fillMaxSize()) {
        if (isLastNull.not()) KinopoiskRating(rating = kinopoiskRating!!) else DevRating(rating = devRating!!)
        if (isLastNull.not()) {
            if (devRating == null) EmptyRatingViewItem() else DevRating(rating = devRating)
        } else EmptyRatingViewItem()
    }
}

@Composable
private fun RowScope.EmptyRatingViewItem() {
    Spacer(modifier = Modifier.weight(0.5f, fill = true))
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WhattowatchTheme {
        RatingView(kinopoiskRating = "8.4", devRating = "9.5")
    }
}