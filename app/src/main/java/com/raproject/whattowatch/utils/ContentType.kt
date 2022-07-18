package com.raproject.whattowatch.utils

import java.io.Serializable

enum class ContentType(val tableName: String) : Serializable {
    Movies(tableName = DBTable.Movies.tableName()),
    Anime(tableName = DBTable.Anime.tableName()),
    TVShows(tableName = DBTable.TVShows.tableName()),
    Cartoons(tableName = DBTable.Cartoons.tableName()),
    Top100(tableName = DBTable.MainTable.tableName()),
    Favorite(tableName = DBTable.Favorite.tableName())
}
