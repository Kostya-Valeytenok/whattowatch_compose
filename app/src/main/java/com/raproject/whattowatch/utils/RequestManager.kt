package com.raproject.whattowatch.utils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class RequestManager {

    private val mainTable = DBTable.MainTable
    private val genresTable = DBTable.GenresTable
    private val portersTable = DBTable.Posters

    fun SQLiteDatabase.getContentCards(
        contentType: ContentType,
        localization: Localization,
        order: OrderType = OrderType.AscendingOrder,
        orderField: TableRow = mainTable.DevRating
    ): Cursor {
        // _Key, Image, Title, Year, Rating1
        val contentTable = mainTable.tableName(localization)
        val key = mainTable.key
        val cardContentFields = mainTable.cardContentFields.joinToString()
        println("|$cardContentFields|")
        val contentCursor = rawQuery(
            "select $cardContentFields from $contentTable where $key in (${selectAllIn(contentType.tableName)})${order.by(orderField)}", // ktlint-disable max-line-length
            null
        )
        contentCursor.moveToFirst()
        return contentCursor
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

    private fun selectAllIn(table: String): String = "select * from $table"
}
