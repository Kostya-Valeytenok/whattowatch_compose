package com.raproject.whattowatch.utils

enum class ContentType(val tableName: String) {
    Movies(tableName = DBTable.Movies.tableName()),
    Anime(tableName = DBTable.Anime.tableName()),
    TVShows(tableName = DBTable.TVShows.tableName());
}
