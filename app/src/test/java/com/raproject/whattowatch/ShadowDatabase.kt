package com.raproject.whattowatch

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.raproject.whattowatch.repository.DataBase
import com.raproject.whattowatch.repository.getWritableDB
import com.raproject.whattowatch.repository.request.GetRequest
import com.raproject.whattowatch.repository.run
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(DataBase::class)
class ShadowDatabase {

    private val context = RuntimeEnvironment.getApplication().applicationContext

    val shadowDatabaseName = "fakedatabase"

    val databaseHelper = FakeDBHelper(context) as SQLiteOpenHelper

    private inner class FakeDBHelper(context:Context) : SQLiteOpenHelper(context,shadowDatabaseName,null, 1) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE MainTable (\n" +
                    "\t\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY,\n" +
                    "\t\"image\"\tTEXT,\n" +
                    "\t\"title\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"year\"\tNUMERIC NOT NULL,\n" +
                    "\t\"country\"\tTEXT NOT NULL,\n" +
                    "\t\"duration\"\tTEXT NOT NULL,\n" +
                    "\t\"devRating\"\tTEXT NOT NULL,\n" +
                    "\t\"kinopoiskRating\"\tTEXT NOT NULL,\n" +
                    "\t\"director\"\tTEXT NOT NULL,\n" +
                    "\t\"cast\"\tTEXT NOT NULL,\n" +
                    "\t\"description\"\tTEXT NOT NULL)")

            db.execSQL("CREATE TABLE MainTableEN (\n" +
                    "\t\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY,\n" +
                    "\t\"image\"\tTEXT,\n" +
                    "\t\"title\"\tTEXT NOT NULL,\n" +
                    "\t\"year\"\tNUMERIC NOT NULL,\n" +
                    "\t\"country\"\tTEXT NOT NULL,\n" +
                    "\t\"duration\"\tTEXT NOT NULL,\n" +
                    "\t\"devRating\"\tTEXT NOT NULL,\n" +
                    "\t\"kinopoiskRating\"\tTEXT NOT NULL,\n" +
                    "\t\"director\"\tTEXT NOT NULL,\n" +
                    "\t\"cast\"\tTEXT NOT NULL,\n" +
                    "\t\"description\"\tTEXT NOT NULL);")

            db.inputTestDataIntoMainTable()
        }

        private fun SQLiteDatabase.inputTestDataIntoMainTable(){
            execSQL("insert into MainTable values (" +
                    "${dbValue(1)}, " +
                    "${dbValue("films/0B.jpg")}, " +
                    "${dbValue("Начало")}, " +
                    "${dbValue("2010")}, " +
                    "${dbValue("США, Великобритания")}, " +
                    "${dbValue("2 ч 28 мин")}, " +
                    "${dbValue("9.3")}, " +
                    "${dbValue("8.6")}, " +
                    "${dbValue("Кристофер Нолан")}, " +
                    "${dbValue("Леонардо ДиКаприо...")}, " +
                    "${dbValue("Кобб — талантливый вор, лучший из лучших...")})")

            execSQL("insert into MainTableEN values (" +
                    "${dbValue(1)}, " +
                    "${dbValue("films/0B.jpg")}, " +
                    "${dbValue("Inception")}, " +
                    "${dbValue("2010")}, " +
                    "${dbValue("Germany, USA")}, " +
                    "${dbValue("148 min.")}, " +
                    "${dbValue("9.3")}, " +
                    "${dbValue("8.6")}, " +
                    "${dbValue("Christopher Nolan")}, " +
                    "${dbValue("Leonardo DiCaprio,...")}, " +
                    "${dbValue("Dom Cobb is a skilled thief, ...")})")

            execSQL("insert into MainTableEN values (" +
                    "${dbValue(2)}, " +
                    "${dbValue("films/1B.jpg")}, " +
                    "${dbValue("Interstellar")}, " +
                    "${dbValue("2014")}, " +
                    "${dbValue("USA, UK, Canada")}, " +
                    "${dbValue("169 min.")}, " +
                    "${dbValue("8.9")}, " +
                    "${dbValue("8.6")}, " +
                    "${dbValue("Christopher Nolan")}, " +
                    "${dbValue("Ellen Burstyn, ...")}, " +
                    "${dbValue("When droughts, dust storms, and  ...")})")

            execSQL("insert into MainTable values (" +
                    "${dbValue(2)}, " +
                    "${dbValue("films/1B.jpg")}, " +
                    "${dbValue("Интерстеллар")}, " +
                    "${dbValue("2014")}, " +
                    "${dbValue("США, Великобритания, Канада")}, " +
                    "${dbValue("2 ч 49 мин")}, " +
                    "${dbValue("8.9")}, " +
                    "${dbValue("8.6")}, " +
                    "${dbValue("Кристофер Нолан")}, " +
                    "${dbValue("Мэттью МакКонахи, ...")}, " +
                    "${dbValue("Когда засуха, пыльные бури и ...")})")

            execSQL("insert into MainTable values (" +
                    "${dbValue(3)}, " +
                    "${dbValue("films/23B.jpg")}, " +
                    "${dbValue("Вечное сияние чистого разума")}, " +
                    "${dbValue("2004")}, " +
                    "${dbValue("США")}, " +
                    "${dbValue("108 мин.")}, " +
                    "${dbValue("9.6")}, " +
                    "${dbValue("8.0")}, " +
                    "${dbValue("Мишель Гондри")}, " +
                    "${dbValue("Джим Керри, ...")}, " +
                    "${dbValue("Застенчивый и меланхоличный Джоэл ...")})")

            execSQL("insert into MainTableEN values (" +
                    "${dbValue(3)}, " +
                    "${dbValue("films/23B.jpg")}, " +
                    "${dbValue("Eternal Sunshine of the Spotless Mind")}, " +
                    "${dbValue("2004")}, " +
                    "${dbValue("USA")}, " +
                    "${dbValue("108 min.")}, " +
                    "${dbValue("9.6")}, " +
                    "${dbValue("8.0")}, " +
                    "${dbValue("Michel Gondry")}, " +
                    "${dbValue("Jim Carrey, ...")}, " +
                    "${dbValue("A man, Joel Barish, heartbroken that his girlfriend ...")})")

        }

        private fun dbValue(value:Any): String {
            return "\"$value\""
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

    }

    @Implementation
    suspend fun <T> execute(request: GetRequest<T>): T {
        println("Make test request")
        return databaseHelper.use {request.run(scope = it.getWritableDB())  }
    }
}