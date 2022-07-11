package com.raproject.whattowatch.test_database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import com.raproject.whattowatch.R
import com.raproject.whattowatch.utils.Localization
import org.robolectric.RuntimeEnvironment
import java.io.ByteArrayOutputStream

object BuildPostersTableTask : DatabaseBuildTask() {

    private val context = RuntimeEnvironment.getApplication().applicationContext

    override fun SQLiteDatabase.buildTask() {
        execSQL("CREATE TABLE \"Posters\" (\n" +
                "\t\"contentId\"\tINTEGER NOT NULL UNIQUE PRIMARY KEY,\n" +
                "\t\"image\"\tBLOB);")
    }

    override fun SQLiteDatabase.inputData() {
        addImage(id = 1)
        addImage(id = 2)
        addImage(id = 3)
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
}