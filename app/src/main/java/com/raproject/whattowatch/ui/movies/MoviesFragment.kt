package com.raproject.whattowatch.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.raproject.whattowatch.ui.MoviesScreen
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val vm: MoviesFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm.getMovies()
        return ComposeView(requireContext()).apply {
            setContent {
                val movies = vm.movies.value
                val loadingVisibility = vm.moviesLoadingStatus.value
                WhattowatchTheme {
                    MoviesScreen(movies, loadingVisibility)
                }
            }
        }
    }
}
