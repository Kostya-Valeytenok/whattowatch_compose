package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle

class GetIsInFavoriteStatus(params:Bundle) : GetRequest<Boolean>() {


    companion object {
        private const val CONTENT_ID = "CONTENT_ID"
        fun createParams(contentId: String): Bundle = Bundle().apply { putString(CONTENT_ID, contentId) }
    }

    private val contentId: String = params.getString(CONTENT_ID, "")

    override suspend fun SQLiteDatabase.runRequest(): Result<Boolean> {
        return safeGetRequest("select * from ${favoriteTable.tableName()} where ${favoriteTable.key} = $contentId"){
            this.count != 0
        }
    }
}