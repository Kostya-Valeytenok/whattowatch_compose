package com.raproject.whattowatch.models

import com.raproject.whattowatch.ui.ContentInfoView

data class ContentViewModel(
    val id: String? = null,
    val posterURL: String = "",
    val contentItems: List<ContentInfoView> = emptyList()
)