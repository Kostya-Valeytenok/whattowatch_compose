package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase

class PostContentIntoFavorite(contentId:String) : PostRequest<String>(contentId) {

    override suspend fun SQLiteDatabase.runRequest() {
        execSQL("insert into Favorite values (${data})")
    }
}