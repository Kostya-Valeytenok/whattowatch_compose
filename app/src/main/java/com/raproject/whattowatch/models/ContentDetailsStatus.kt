package com.raproject.whattowatch.models

sealed class ContentDetailsStatus{

    object onLoading : ContentDetailsStatus()

    data class onLoaded(
        val id: String,
        val contentItems: Map<DataContentType, Any> = mapOf()
    ) : ContentDetailsStatus()

    data class OnFailed(val throwable: Throwable) : ContentDetailsStatus()
}
