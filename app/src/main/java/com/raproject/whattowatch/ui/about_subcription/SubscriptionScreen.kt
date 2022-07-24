package com.raproject.whattowatch.ui.about_subcription

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.raproject.whattowatch.R
import com.raproject.whattowatch.ui.theme.Pink80


private val constraintSet = ConstraintSet {
    val subscriptionButton = createRefFor("subscriptionButton")
    val subscriptionInstruction = createRefFor("subscriptionInstruction")

    constrain(subscriptionButton) {
        bottom.linkTo(parent.bottom, 16.dp)
        start.linkTo(parent.start, 0.dp)
        end.linkTo(parent.end, 0.dp)
    }

    constrain(subscriptionInstruction) {
        bottom.linkTo(subscriptionButton.bottom, 8.dp)
        start.linkTo(parent.start, 16.dp)
        end.linkTo(parent.end, 16.dp)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubscriptionScreen(onSubscriptionPlanSelect: () -> Unit) {
    Column {
        Text(text = "#WHATTOWATCH SUBSCRIPTION")
        Spacer(modifier = Modifier.height(16.dp))
        AppLogo()
        ConstraintLayout(constraintSet) {

            Text(
                text = "Click on the subscribe button to choose a preferred plan",
                Modifier.layoutId("subscriptionInstruction")
            )

            IconButton(onClick = { /*TODO*/ }, Modifier.layoutId("subscriptionButton")) {
                Image(
                    painter = painterResource(R.drawable.subscribe),
                    contentDescription = "content description",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.6f)
                )
            }
        }

    }
}

@Composable
private fun AppLogo() {
    Card(shape = CircleShape, backgroundColor = Pink80) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "content description",
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
        )
    }
}