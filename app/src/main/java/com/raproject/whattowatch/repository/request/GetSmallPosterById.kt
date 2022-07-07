package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle

class GetSmallPosterById(private val params: Bundle) : GetRequest<Bitmap>() {

    companion object {
        private const val CONTENT_ID = "CONTENT_ID"

        fun createParams(contentId: String): Bundle =
            Bundle().apply { putString(CONTENT_ID, contentId) }
    }

    private val contentId: String
        get() = params.getString(CONTENT_ID, "")


    override suspend fun SQLiteDatabase.runRequest(): Result<Bitmap> {
        val key = portersTable.key
        val table = portersTable.tableName()
        val field = portersTable.Image.name

        return safeGetRequest("SELECT $field FROM $table WHERE $key = $contentId") {
            BitmapFactory.decodeByteArray(this.getBlob(0), 0, this.getBlob(0)!!.size)
        }
    }
}