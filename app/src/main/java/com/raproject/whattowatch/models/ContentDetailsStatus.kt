package com.raproject.whattowatch.models

import com.raproject.whattowatch.ui.about_content.AboutContentViewModel

sealed class ContentDetailsStatus{

    object onLoading : ContentDetailsStatus()

    data class ContentInformationModel(
        val id:String,
        val posterUrl:String,
        val title:String,
        var genres: String,
        val year: String,
        val duration:String,
        val isInFavorite:Boolean
    ) : ContentDetailsStatus()

    data class OnFailed(val throwable: Throwable) : ContentDetailsStatus()
}
