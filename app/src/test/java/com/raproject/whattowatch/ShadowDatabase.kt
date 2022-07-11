package com.raproject.whattowatch

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.getWritableDB
import com.raproject.whattowatch.repository.request.GetRequest
import com.raproject.whattowatch.repository.run
import com.raproject.whattowatch.test_database.DatabaseBuilder
import com.raproject.whattowatch.test_database.DatabaseBuilder.build
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import java.io.ByteArrayOutputStream


@Implements(DataBase::class)
class ShadowDatabase {

    private val context = RuntimeEnvironment.getApplication().applicationContext

    val shadowDatabaseName = "fakedatabase"

    val databaseHelper = FakeDBHelper(context) as SQLiteOpenHelper

    private inner class FakeDBHelper(context:Context) : SQLiteOpenHelper(context,shadowDatabaseName,null, 1) {

        override fun onCreate(db: SQLiteDatabase) { db.build() }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

    }

    @Implementation
    suspend fun <T> execute(request: GetRequest<T>): Result<T> {
        println("Make test request")
        return databaseHelper.use {request.run(scope = it.getWritableDB())  }
    }
}