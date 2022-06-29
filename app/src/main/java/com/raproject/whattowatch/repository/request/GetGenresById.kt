package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.raproject.whattowatch.utils.Localization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetGenresById(private val params: Bundle) : GetRequest<String>() {

    companion object {
        private const val LOCALIZATION_KEY = "LOCALIZATION_KEY"
        private const val CONTENT_ID = "CONTENT_ID"

        fun createParams(contentId:String, localization: Localization): Bundle {
            val params = Bundle()
            params.putString(CONTENT_ID, contentId)
            params.putSerializable(LOCALIZATION_KEY, localization)
            return params
        }
    }

    private val contentId: String
        get() = params.getString(CONTENT_ID, "")

    private val localization: Localization
        get() = params.getSerializable(LOCALIZATION_KEY) as Localization

    override suspend fun SQLiteDatabase.runRequest(): String = withContext(Dispatchers.Default) {
        val generesDBTable = genresTable.tableName(localization)
        val key = genresTable.key

        return@withContext safeGetRequest(
            sqlCommand = "select * from $generesDBTable WHERE $key = $contentId") {
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