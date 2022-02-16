package com.raproject.whattowatch.utils

sealed class DBTable {

    abstract val key: String

    fun tableName(locale: Localization = Localization.English): String {
        return when (this) {
            MainTable -> return when (locale) {
                Localization.English -> "MainTableEN"
                Localization.Russian -> "MainTable"
            }
            GenresTable -> when (locale) {
                Localization.English -> "GenresKeysEN"
                Localization.Russian -> "GenresKeys"
            }
            Anime -> "Anime"
            Movies -> "Films"
            TVShows -> "Series"
            Posters -> "Posters"
            Cartoons -> "Cartoons"
        }
    }

    object MainTable : DBTable() {
        val Title = TableRow("title")
        val Year = TableRow("year")
        val Country = TableRow("country")
        val Duration = TableRow("duration")
        var DevRating = TableRow("devRating")
        val KinopoiskRating = TableRow("kinopoiskRating")
        val Director = TableRow("director")
        val Cast = TableRow("cast")
        val Description = TableRow("description")
        val Image = TableRow("image")

        val cardContentFields = listOf<String>(key, Image.name, Title.name, Year.name, DevRating.name) // ktlint-disable max-line-length

        override val key: String
            get() = "contentId"
    }

    object GenresTable : DBTable() {
        override val key: String
            get() = "contentId"
    }

    object Anime : DBTable() {
        override val key: String
            get() = "contentId"
    }

    object TVShows : DBTable() {
        override val key: String
            get() = "contentId"
    }

    object Movies : DBTable() {
        override val key: String
            get() = "contentId"
    }

    object Cartoons : DBTable() {
        override val key: String
            get() = "contentId"
    }

    object Posters : DBTable() {
        override val key: String
            get() = "contentId"

        val Image = TableRow("image")
    }
}
