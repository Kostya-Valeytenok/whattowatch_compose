package com.raproject.whattowatch.utils

enum class ContentType(val tableName: String,val screenName:String) {
    Movies("Films","Movies")
    , Anime("Anime", "Anime"),
    TVShows("Series", "TV Shows");
}
