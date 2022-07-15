package com.raproject.whattowatch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.raproject.whattowatch.models.ContentViewModel
import com.raproject.whattowatch.ui.theme.LikeColor
import com.raproject.whattowatch.ui.theme.TextColor
import com.raproject.whattowatch.utils.EMPTY_URI
import com.raproject.whattowatch.utils.PosterShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentInformationScreen(
    model: ContentViewModel,
    isInFavoriteState: Boolean,
    onBackClickAction: () -> Unit = {},
    manageLikeStatusAction: ((Throwable) -> Unit) -> Unit = {}
) {
    Column {
        val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)

        val spreadOfPosterState = remember { mutableStateOf(false) }
        val likeButtonVisibilityState =
            MutableTransitionState(model.id != null && spreadOfPosterState.value.not())
        val posterURIState = remember { mutableStateOf<Any>(EMPTY_URI.toUri()) }
        val posterLoadedState = remember { mutableStateOf(true) }


        LaunchedEffect(key1 = model.posterURITask, block = {
            withContext(Dispatchers.Default) {
                posterLoadedState.value = true
                val result = model.posterURITask?.await()
                result?.let {
                    posterURIState.value = it
                } ?: kotlin.run { posterLoadedState.value = false }
            }
        })

        BackdropScaffold(
            appBar = {},
            backLayerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.80f)
                ) {

                    GoBackIcon(goBackAction = onBackClickAction)

                    PosterImage(
                        uri = posterURIState.value,
                        loadingProgressState = posterLoadedState,
                        spreadOfPosterState = spreadOfPosterState
                    )

                    LikeIconButtonWithVisibilityState(
                        onClickAction = manageLikeStatusAction,
                        isInFavorite = isInFavoriteState,
                        visibilityState = likeButtonVisibilityState
                    )
                }
            },
            frontLayerContent = { ContentInfoList(model = model) },
            scaffoldState = scaffoldState,
            persistentAppBar = true,
            backLayerBackgroundColor = MaterialTheme.colorScheme.tertiary,
            peekHeight = 0.dp,
            headerHeight = 30.dp
        )
    }
}

@Composable
private fun BoxScope.GoBackIcon(goBackAction: () -> Unit) {
    IconButton(
        onClick = { goBackAction.invoke() }, modifier = Modifier
            .padding(top = 20.dp, start = 0.dp)
            .align(Alignment.TopStart)
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Like Status",
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            tint = TextColor.value
        )

    }
}

@Composable
private fun BoxScope.LikeIconButtonWithVisibilityState(
    onClickAction: ((Throwable) -> Unit) -> Unit,
    isInFavorite: Boolean,
    visibilityState: MutableTransitionState<Boolean>
) {
    AnimatedVisibility(
        visibleState = visibilityState,
        modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        LikeIconButton(onClickAction = onClickAction, isInFavorite = isInFavorite)
    }
}

@Composable
private fun LikeIconButton(
    onClickAction: ((Throwable) -> Unit) -> Unit,
    isInFavorite: Boolean
) {
    IconButton(
        onClick = {
            println("FFF")
            onClickAction.invoke({ error -> })
        }) {
        Icon(
            imageVector = if (isInFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Like Status",
            modifier = Modifier
                .padding(bottom = 8.dp)
                .height(44.dp)
                .width(44.dp),
            tint = LikeColor
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.PosterImage(
    uri: Any,
    loadingProgressState: MutableState<Boolean>,
    spreadOfPosterState: MutableState<Boolean>
) {
    val posterShape = PosterShape(
        cornerRadius = with(LocalDensity.current) { 44.dp.toPx() },
        cutPadding = with(LocalDensity.current) { 16.dp.toPx() },
        existButtonPadding = with(LocalDensity.current) { 96.dp.toPx() })

    val rounderState = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)

    val loadingProgressBarVisibilityState = MutableTransitionState(loadingProgressState.value)

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .align(Alignment.Center)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(
                top = if (spreadOfPosterState.value.not()) 8.dp else 0.dp,
                bottom = if (spreadOfPosterState.value.not()) 16.dp else 0.dp,
                start = if (spreadOfPosterState.value.not()) 4.dp else 0.dp,
                end = if (spreadOfPosterState.value.not()) 4.dp else 0.dp
            ),
        shape = if (spreadOfPosterState.value.not()) posterShape else rounderState,
        onClick = {
            spreadOfPosterState.value = spreadOfPosterState.value.not()
        }
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxHeight(),
            model = uri,
            contentDescription = "Poster",
            contentScale = ContentScale.FillHeight,
            onState = {
                loadingProgressState.manageByImageLoadingState(
                    uri = uri,
                    loadingState = it
                )
            }
        )
    }

    BoxProgressBar(visibilityState = loadingProgressBarVisibilityState)
}


private fun MutableState<Boolean>.manageByImageLoadingState(
    uri: Any,
    loadingState: AsyncImagePainter.State
) {
    when (loadingState) {
        is AsyncImagePainter.State.Empty -> {
            value = false
        }
        is AsyncImagePainter.State.Loading -> value = true
        is AsyncImagePainter.State.Success -> value = false
        is AsyncImagePainter.State.Error -> {
            value = uri.toString() == EMPTY_URI
        }
    }
}

@Composable
fun ContentInfoList(model: ContentViewModel) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
        items(model.contentItems) { it.build() }
    }
}


sealed class ContentInfoView {

    @Composable
    abstract fun build()

    data class Title(private val title: String) : ContentInfoView() {

        @Composable
        override fun build() {
            Text(
                text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
        }
    }

    data class Genres(private val genres: String) : ContentInfoView() {

        @Composable
        override fun build() {
            Text(
                text = genres,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
        }
    }

    data class YearPlusDuration(private val text: String) : ContentInfoView() {

        @Composable
        override fun build() {
            Text(
                text = text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
        }
    }

    data class Space(private val spaceInDP: Int) : ContentInfoView() {

        @Composable
        override fun build() {
            Spacer(modifier = Modifier.height(spaceInDP.dp))
        }

    }

    class Cast : ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Rate : ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Director : ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }

    class Description : ContentInfoView() {

        @Composable
        override fun build() {
            // TODO: "Not yet implemented"
        }
    }
}

