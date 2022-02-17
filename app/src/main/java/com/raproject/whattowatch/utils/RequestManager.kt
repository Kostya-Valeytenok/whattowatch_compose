package com.raproject.whattowatch.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestManager {

    private val mainTable = DBTable.MainTable
    private val genresTable = DBTable.GenresTable
    private val portersTable = DBTable.Posters

    suspend fun SQLiteDatabase.getContentCards(
        contentType: ContentType,
        localization: Localization,
        orderCommand: String = ""
    ): Cursor = withContext(Dispatchers.Default) {
        // _Key, Image, Title, Year, Rating1
        val contentTable = mainTable.tableName(localization)
        val key = mainTable.key
        val cardContentFields = if (contentType == ContentType.Top100)
            mainTable.cardContentFieldsWithRating.joinToString()
        else mainTable.cardContentFields.joinToString()
        println("|$cardContentFields|")

        val contentCursor = if (contentType == ContentType.Top100) {
            rawQuery(
                "select $cardContentFields from $contentTable where $key in (${selectAllForTop(contentType,localization,orderCommand)})$orderCommand", // ktlint-disable max-line-length
                null
            )
        } else rawQuery(
            "select $cardContentFields from $contentTable where $key in (${selectAllIn(contentType.tableName)})$orderCommand", // ktlint-disable max-line-length
            null
        )
        contentCursor.moveToFirst()
        return@withContext contentCursor
    }

    fun SQLiteDatabase.getGenresForTitle(
        contentId: String,
        localization: Localization
    ): Cursor {
        val generesDBTable = genresTable.tableName(localization)
        val key = genresTable.key
        val genresCursor = rawQuery(
            "${selectAllIn(generesDBTable)} WHERE $key = $contentId", null
        )
        genresCursor.moveToFirst()
        return genresCursor
    }

    fun SQLiteDatabase.getSmallPoster(contentId: String): Cursor {
        val key = portersTable.key
        val table = portersTable.tableName()
        val field = portersTable.Image.name
        val posterData = rawQuery(
            "SELECT $field FROM $table WHERE $key = $contentId", null
        )
        posterData.moveToFirst()
        return posterData
    }

    private fun SQLiteDatabase.selectAllForTop(
        type: ContentType,
        localization: Localization,
        orderCommand: String
    ): String {
        return if (type == ContentType.Top100) {
            val contentTable = mainTable.tableName(localization)
            rawQuery("select ${mainTable.key} from $contentTable$orderCommand LIMIT 100", null).use {
                it.moveToFirst()
                val idList = mutableListOf<String>()
                while (!it.isAfterLast) {
                    idList.add(it.getString(0))
                    it.moveToNext()
                }
                idList.joinToString()
            }
        } else selectAllIn(table = type.tableName)
    }

    private fun selectAllIn(table: String): String = "select * from $table"
}
