package com.raproject.whattowatch.test_database_builder

import android.database.sqlite.SQLiteDatabase
import com.raproject.whattowatch.utils.Localization

object BuildMainTableTask : DatabaseBuildTask() {
    override fun SQLiteDatabase.buildTask() {
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
    }

    override fun SQLiteDatabase.inputData() {
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
            title = "Интерстеллар",
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
}