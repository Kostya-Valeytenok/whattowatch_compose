package com.raproject.whattowatch.test_database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.utils.Localization

object BuildGenresTableTask : DatabaseBuildTask() {

    override fun SQLiteDatabase.buildTask() {
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
    }

    override fun SQLiteDatabase.inputData() {
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
}