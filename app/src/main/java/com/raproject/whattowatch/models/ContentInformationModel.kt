package com.raproject.whattowatch.models

data class ContentInformationModel(
    val posterUrl:String,
    val title:String,
    var genres: String,
    val year: String,
    val duration:String,
    val isInFavorite:Boolean
)
