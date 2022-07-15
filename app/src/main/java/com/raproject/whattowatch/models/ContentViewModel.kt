package com.raproject.whattowatch.models

import com.raproject.whattowatch.ui.ContentInfoView
import kotlinx.coroutines.Deferred

data class ContentViewModel(
    val id: String? = null,
    val posterURITask: Deferred<*>? = null,
    val contentItems: List<ContentInfoView> = emptyList()
)