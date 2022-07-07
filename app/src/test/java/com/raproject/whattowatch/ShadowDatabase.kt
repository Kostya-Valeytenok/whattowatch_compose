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
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import java.io.ByteArrayOutputStream


@Implements(DataBase::class)
class ShadowDatabase {

    private val context = RuntimeEnvironment.getApplication().applicationContext
    private val mainTable = DBTable.MainTable
    private val genresTable = DBTable.GenresTable

    val shadowDatabaseName = "fakedatabase"

    val databaseHelper = FakeDBHelper(context) as SQLiteOpenHelper

    private inner class FakeDBHelper(context:Context) : SQLiteOpenHelper(context,shadowDatabaseName,null, 1) {

        override fun onCreate(db: SQLiteDatabase) {
            db.createMainTable()
            db.createGenresTable()
            db.createPostersTable()
        }

        private fun SQLiteDatabase.createMainTable(){
            Localization.values().forEach {
                execSQL("CREATE TABLE ${mainTable.tableName(locale = it)} (\n" +
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
            }
            inputTestDataIntoMainTable()
        }

        private fun SQLiteDatabase.createGenresTable(){
            Localization.values().forEach {
                execSQL("CREATE TABLE ${genresTable.tableName(locale = it)} (\n" +
                        "\t\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY,\n" +
                        "\t\"Biograph\"\tTEXT,\n" +
                        "\t\"Action\"\tTEXT,\n" +
                        "\t\"Western\"\tTEXT,\n" +
                        "\t\"Military\"\tTEXT,\n" +
                        "\t\"Detctive\"\tTEXT,\n" +
                        "\t\"Document\"\tTEXT,\n" +
                        "\t\"Drama\"\tTEXT,\n" +
                        "\t\"Historical\"\tTEXT,\n" +
                        "\t\"Comedy\"\tTEXT,\n" +
                        "\t\"Crime\"\tTEXT,\n" +
                        "\t\"Melodrama\"\tTEXT,\n" +
                        "\t\"Musical\"\tTEXT,\n" +
                        "\t\"Adventure\"\tTEXT,\n" +
                        "\t\"Family\"\tTEXT,\n" +
                        "\t\"Sport\"\tTEXT,\n" +
                        "\t\"Thriller\"\tTEXT,\n" +
                        "\t\"Horror\"\tTEXT,\n" +
                        "\t\"Fantastic\"\tTEXT,\n" +
                        "\t\"Fantasy\"\tTEXT)")
            }
            inputTestDataIntoGenresTable()
        }

        private fun SQLiteDatabase.inputTestDataIntoMainTable(){
            addContent(
                id = 1,
                posterURL = "films/0B.jpg",
                title = "Начало",
                year = "2010",
                country = "США, Великобритания",
                duration = "2 ч 28 мин",
                devRating = "9.3",
                kinopoiskRating = "8.6",
                director = "Кристофер Нолан",
                staff = "Леонардо ДиКаприо...",
                description = "Кобб — талантливый вор, лучший из лучших...",
                localization = Localization.Russian
            )
            addContent(
                id = 1,
                posterURL = "films/0B.jpg",
                title = "Inception",
                year = "2010",
                country = "Germany, USA",
                duration = "148 min.",
                devRating = "9.3",
                kinopoiskRating = "8.6",
                director = "Christopher Nolan",
                staff = "Leonardo DiCaprio...",
                description = "Dom Cobb is a skilled thief ...",
                localization = Localization.English
            )

            addContent(
                id = 2,
                posterURL = "films/1B.jpg",
                title = "Interstellar",
                year = "2014",
                country = "США, Великобритания, Канада",
                duration = "2 ч 49 мин",
                devRating = "8.9",
                kinopoiskRating = "8.6",
                director = "Кристофер Нолан",
                staff = "Мэттью МакКонахи...",
                description = "Когда засуха, пыльные бури и ...",
                localization = Localization.Russian
            )

            addContent(
                id = 2,
                posterURL = "films/1B.jpg",
                title = "Interstellar",
                year = "2014",
                country = "USA, UK, Canada",
                duration = "169 min.",
                devRating = "8.9",
                kinopoiskRating = "8.6",
                director = "Christopher Nolan",
                staff = "Ellen Burstyn ...",
                description = "When droughts, dust storms, and  ...",
                localization = Localization.English
            )

            addContent(
                id = 3,
                posterURL = "films/23B.jpg",
                title = "Вечное сияние чистого разума",
                year = "2004",
                country = "США",
                duration = "108 мин.",
                devRating = "9.6",
                kinopoiskRating = "8.0",
                director = "Мишель Гондри",
                staff = "Джим Керри...",
                description = "Застенчивый и меланхоличный Джоэл ...",
                localization = Localization.Russian
            )

            addContent(
                id = 3,
                posterURL = "films/23B.jpg",
                title = "Eternal Sunshine of the Spotless Mind",
                year = "2004",
                country = "USA",
                duration = "108 min.",
                devRating = "9.6",
                kinopoiskRating = "8.0",
                director = "Michel Gondry",
                staff = "Jim Carrey, ...",
                description = "ЗA man, Joel Barish, heartbroken that his girlfriend ...",
                localization = Localization.English
            )

        }

        private fun SQLiteDatabase.addContent(
            id:Int,
            posterURL:String,
            title:String,
            year: String,
            country:String,
            duration:String,
            devRating:String,
            kinopoiskRating:String,
            director:String,
            staff:String,
            description:String,
            localization: Localization
        ){
            execSQL("insert into ${mainTable.tableName(locale = localization)} values (" +
                    "${dbValue(id)}, " +
                    "${dbValue(posterURL)}, " +
                    "${dbValue(title)}, " +
                    "${dbValue(year)}, " +
                    "${dbValue(country)}, " +
                    "${dbValue(duration)}, " +
                    "${dbValue(devRating)}, " +
                    "${dbValue(kinopoiskRating)}, " +
                    "${dbValue(director)}, " +
                    "${dbValue(staff)}, " +
                    "${dbValue(description)})")
        }

        private fun dbValue(value:Any): String {
            return "\"$value\""
        }

        private fun SQLiteDatabase.inputTestDataIntoGenresTable(){
            addGenreForContent(
                id = 1,
                action = "Action, ",
                detective = "Detective, ",
                drama = "Drama, ",
                thriller = "Thriller, ",
                fantastic = "Fantastic",
                localization = Localization.English
            )
            addGenreForContent(
                id = 1,
                action = "Боевик, ",
                detective = "Детектив, ",
                drama = "Драма, ",
                thriller = "Триллер, ",
                fantastic = "Фантастика",
                localization = Localization.Russian
            )

            addGenreForContent(
                id = 2,
                drama = "Drama, ",
                adventure = "Adventure, ",
                fantastic = "Fantastic",
                localization = Localization.English
            )
            addGenreForContent(
                id = 2,
                drama = "Драма, ",
                adventure = "Приключения, ",
                fantastic = "Фантастика",
                localization = Localization.Russian
            )

            addGenreForContent(
                id = 3,
                drama = "Drama, ",
                melodrama = "Melodrama, ",
                fantastic = "Fantastic",
                localization = Localization.English
            )

            addGenreForContent(
                id = 3,
                drama = "Драма, ",
                melodrama = "Мелодрама, ",
                fantastic = "Фантастика",
                localization = Localization.Russian
            )
        }

        private fun SQLiteDatabase.addGenreForContent(
            id:Int,
            biograph:String? = null,
            action:String? = null,
            western:String? = null,
            military:String? = null,
            detective:String? = null,
            document:String? = null,
            drama:String? = null,
            historical:String? = null,
            comedy:String? = null,
            crime: String? = null,
            melodrama:String? = null,
            musical:String? = null,
            adventure:String? = null,
            sport:String? = null,
            thriller:String? = null,
            horror:String? = null,
            fantastic:String? = null,
            fantasy:String? = null,
            localization: Localization
        ){
            val genres = ContentValues()
            genres.put("contentId",id)
            genres.put("Biograph",biograph)
            genres.put("Action",action)
            genres.put("Western",western)
            genres.put("Military",military)
            genres.put("Detctive",detective)
            genres.put("Document",document)
            genres.put("Drama",drama)
            genres.put("Historical",historical)
            genres.put("Comedy",comedy)
            genres.put("Crime",crime)
            genres.put("Melodrama",melodrama)
            genres.put("Musical",musical)
            genres.put("Adventure",adventure)
            genres.put("Sport",sport)
            genres.put("Thriller",thriller)
            genres.put("Horror",horror)
            genres.put("Fantastic",fantastic)
            genres.put("Fantasy",fantasy)

            insert(genresTable.tableName(locale = localization), null, genres)
        }

        private fun SQLiteDatabase.createPostersTable(){
            execSQL("CREATE TABLE \"Posters\" (\n" +
                    "\t\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY,\n" +
                    "\t\"image\"\tBLOB);")
            inputTestDataIntoPostersTable()
        }

        private fun SQLiteDatabase.inputTestDataIntoPostersTable(){
            addImage(id = 1)
            addImage(id = 2)
            addImage(id = 3)
        }

        private fun  SQLiteDatabase.addImage(id:Int, @DrawableRes imageId:Int = R.drawable.t_image){
            val poster = ContentValues()
            val image = (context.getDrawable(imageId) as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val imageData: ByteArray = stream.toByteArray()
            poster.put("contentId",id)
            poster.put("image", imageData)
            insert("Posters",null,poster)
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