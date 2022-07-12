package com.raproject.whattowatch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raproject.whattowatch.models.ContentInformationModel
import com.raproject.whattowatch.utils.PosterShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentInformation(model:ContentInformationModel){
    Column {
        val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)

        BackdropScaffold(
            appBar = { /*TODO*/ },
            backLayerContent = { PosterImage(url = model.posterUrl)},
            frontLayerContent = { ContentInfoList(model = model) },
            scaffoldState = scaffoldState,
            persistentAppBar = true,
            backLayerBackgroundColor =  MaterialTheme.colorScheme.tertiary,
            peekHeight = 0.dp,
            headerHeight = 30.dp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PosterImage(url:String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp)
        .fillMaxHeight(fraction = 0.80f)){
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 16.dp),
            shape = PosterShape(
                cornerRadius = with(LocalDensity.current) { 44.dp.toPx() },
                cutPadding =with(LocalDensity.current) { 16.dp.toPx() },
                existButtonPadding = with(LocalDensity.current) { 96.dp.toPx() })
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight(),
                model = url,
                contentDescription =  "Poster",
            contentScale = ContentScale.FillHeight)
        }

        IconButton(onClick = {  },modifier = Modifier
            .align(Alignment.BottomCenter)) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Like Status", modifier = Modifier
                .height(44.dp)
                .width(44.dp) )
        }
    }
}

@Composable
fun ContentInfoList(model:ContentInformationModel){
    val contentList = listOf<ContentInfoView>(
        ContentInfoView.Title(title = model.title),
        ContentInfoView.Space(spaceInDP = 4),
        ContentInfoView.YearPlusDuration(year = model.year, duration = model.duration),
        ContentInfoView.Space(spaceInDP = 8),
        ContentInfoView.Genres(genres = model.genres),
    )
    LazyColumn{
        items(contentList) {  it.build() }
    }
}

private sealed class ContentInfoView{

    @Composable
    abstract fun build()

    class Title(private val title:String) :ContentInfoView() {

        @Composable
        override fun build() {
            Text(text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth())
        }
    }

    class Genres(private val genres:String): ContentInfoView() {

        @Composable
        override fun build() {
            Text(text = genres,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth())
        }
    }

    class YearPlusDuration(private val year: String, private val duration: String) : ContentInfoView() {

        @Composable
        override fun build() {
            Text(text = "$year, $duration",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth())
        }
    }

    class Space(private val spaceInDP:Int):ContentInfoView(){

        @Composable
        override fun build() {
            Spacer(modifier = Modifier.height(spaceInDP.dp))
        }

    }

    class Cast:ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Rate:ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Director:ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Description:ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }
}

