package com.raproject.whattowatch.ui.about_content

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raproject.whattowatch.models.ContentInformationModel
import com.raproject.whattowatch.repository.fakeImageURL
import com.raproject.whattowatch.ui.ContentInformation
import com.raproject.whattowatch.ui.theme.WhattowatchTheme

class AboutContentActivity : ComponentActivity() {

    companion object{

        private val KEY_CONTENT_ID:String ="KEY_CONTENT_ID"

        fun getLaunchIntent(context: Context, contentId:String): Intent {
            val intent = Intent(context, AboutContentActivity::class.java)
            intent.putExtra(KEY_CONTENT_ID,contentId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhattowatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentInformation(model = ContentInformationModel(
                        posterUrl = fakeImageURL,
                        title = "Inception",
                        year = "2010",
                        genres = "Action, Detective, Drama, Thriller, Fantastic",
                        duration = "108 min"
                    )
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
    ContentInformation(model = ContentInformationModel(
        posterUrl = fakeImageURL,
        title = "Inception",
        year = "2010",
        genres = "Action, Detective, Drama, Thriller, Fantastic",
        duration = "108 min"
    ))
}