package com.raproject.whattowatch.ui.content_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.raproject.whattowatch.ui.ContentScreen
import com.raproject.whattowatch.ui.DrawerScreen
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import kotlinx.coroutines.launch

class AppActivity : ComponentActivity() {

    private val vm: AppActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var screens = DrawerScreen.screens
        val navigationAction: (DrawerScreen) -> Unit = {
            lifecycleScope.launch {
                //  vm.screenTypeRX.value = it
                vm.screenTypeRX.emit(it)
            }
        }

        setContent {
            WhattowatchTheme {
                val content = vm.content.collectAsState().value
                val loadingVisibility = vm.loadingStatus.collectAsState().value
                val type = vm.screenTypeRX.collectAsState().value
                ContentScreen(content, loadingVisibility, type, navigationAction)
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
