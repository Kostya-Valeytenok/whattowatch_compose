package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.raproject.whattowatch.utils.Localization

class GetGenresById(params: Bundle) : GetRequest<String>() {

    companion object {
        private const val LOCALIZATION_KEY = "LOCALIZATION_KEY"
        private const val CONTENT_ID = "CONTENT_ID"

        fun createParams(contentId: String, localization: Localization): Bundle = Bundle().apply {
            putString(CONTENT_ID, contentId)
            putSerializable(LOCALIZATION_KEY, localization)
        }
    }

    private val contentId: String = params.getString(CONTENT_ID, "")

    private val localization: Localization = params.getSerializable(LOCALIZATION_KEY) as Localization

    override suspend fun SQLiteDatabase.runRequest(): String  {
        return  getGenres()
    }

    private suspend fun SQLiteDatabase.getGenres():String{
        val generesDBTable = genresTable.tableName(localization)
        val key = genresTable.key

        return safeGetRequest(
            sqlCommand = "select * from $generesDBTable WHERE $key = $contentId"
        ) {
            var genesTemp = ""
            for (i in 1..19) {
                getString(i)?.let {
                    genesTemp += it
                }
            }
            genesTemp
        }
    }
}