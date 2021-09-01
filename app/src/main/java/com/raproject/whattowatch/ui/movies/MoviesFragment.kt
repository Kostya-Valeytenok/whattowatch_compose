package com.raproject.whattowatch.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.raproject.whattowatch.models.ContentItem
import com.raproject.whattowatch.ui.ContentCard
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val vm: MoviesFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                val movies = vm.getMovies()
                setContent {
                    WhattowatchTheme {
                        MoviesList(movies)
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesList(movies: List<ContentItem>) {
    LazyColumn {
        items(movies) {
            ContentCard(content = it)
        }
    }
}
