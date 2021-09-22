package com.raproject.whattowatch.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.raproject.whattowatch.ui.SeriesScreen
import com.raproject.whattowatch.ui.theme.WhattowatchTheme
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment() {

    private val vm: SeriesFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm.getSeries()
        return ComposeView(requireContext()).apply {
            setContent {
                val movies = vm.series.value
                val loadingVisibility = vm.seriesLoadingStatus.value
                WhattowatchTheme {
                    SeriesScreen(movies, loadingVisibility)
                }
            }
        }
    }
}
