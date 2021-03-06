package com.raproject.whattowatch.ui.content_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.ui.ContentScreen
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.ui.about_content.AboutContentActivity
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import com.raproject.whattowatch.utils.AppState
import com.raproject.whattowatch.utils.DoubleClickChecker
import com.raproject.whattowatch.utils.Localization

class AppActivity : ComponentActivity() {

    private val vm: AppActivityViewModel by viewModels()

    private val openAboutContentScreenAction: (String) -> Unit = {
        startActivity(AboutContentActivity.getLaunchIntent(this, contentId = it))
    }

    private val isNotDoubleClick: Boolean
        get() = doubleClickVerification.isDoubleClicked.not()

    private val doubleClickVerification = DoubleClickChecker()

    private val setLocalizationAction: suspend () -> Unit = {

        if (isNotDoubleClick) {
            vm.setLocalization(
                if (AppState.localization.value == Localization.English) Localization.Russian
                else Localization.English
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DrawerScreen.screens

        val navigationAction: suspend (DrawerScreen) -> Unit = { vm.setScreenType(it) }
        setContent {
            WhattowatchTheme {

                val content = vm.content.collectAsState().value
                val loadingVisibility = vm.loadingStatus.collectAsState().value
                val type = vm.screenTypeState.collectAsState().value
                ContentScreen(
                    content,
                    loadingVisibility,
                    type,
                    navigationAction,
                    openAboutContentScreenAction,
                    setLocalizationAction
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WhattowatchTheme {
        Greeting("Android")
    }
}
