package com.raproject.whattowatch.ui.about_content

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.models.ContentViewModel
import com.raproject.whattowatch.ui.ContentInformationScreen
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import com.raproject.whattowatch.utils.DoubleClickChecker

class AboutContentActivity : ComponentActivity() {

    companion object {

        private val KEY_CONTENT_ID: String = "KEY_CONTENT_ID"

        fun getLaunchIntent(context: Context, contentId: String): Intent {
            val intent = Intent(context, AboutContentActivity::class.java)
            intent.putExtra(KEY_CONTENT_ID, contentId)
            return intent
        }
    }

    private val doubleClickVerification = DoubleClickChecker()

    private val isNotDoubleClick :Boolean
        get() = doubleClickVerification.isDoubleClicked.not()

    private val viewModel: AboutContentViewModel by viewModels()

    private val goBackAction: () -> Unit = { if (isNotDoubleClick) finish() }

    private val manageFavoriteStatus: ((Throwable)->Unit) -> Unit = {
        viewModel.manageFavoriteStatus(onErrorAction = it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(contentId = intent.getStringExtra(KEY_CONTENT_ID)?:"")
        setContent {
            WhattowatchTheme {

                val isInFavoriteState = viewModel.isInFavoriteState.collectAsState().value
                val contentViewModelState = viewModel.contentState.collectAsState().value
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentInformationScreen(
                        model = contentViewModelState,
                        onBackClickAction = goBackAction,
                        manageLikeStatusAction = manageFavoriteStatus,
                        isInFavoriteState = isInFavoriteState
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ContentInformationScreen(
        model = ContentViewModel(),
        isInFavoriteState = false
    )
}