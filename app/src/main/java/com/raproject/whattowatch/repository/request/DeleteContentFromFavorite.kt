package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase

class DeleteContentFromFavorite(contentId:String) : PostRequest<String>(contentId) {

    override suspend fun SQLiteDatabase.runRequest() {
        execSQL("delete from ${favoriteTable.tableName()} where ${favoriteTable.key} = (${data})")
    }
}