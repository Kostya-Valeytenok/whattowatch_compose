package com.raproject.whattowatch.repository

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseHelper(context: Context, private val DB_NAME: String = "content.db") :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    private var mNeedUpdate = false

    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            kotlin.runCatching {
                this.writableDatabase
            }.onSuccess { db -> db.tryToSaveOldData() }.onFailure { return }
        }
    }

    private fun SQLiteDatabase.tryToSaveOldData() {
        val cur1 = rawQuery("select _Key from Wanttowatch", null)
        val cur2 = rawQuery("select _Key from Saw", null)
        cur1.moveToFirst()
        cur2.moveToFirst()
        close()
        val dbFile = File(DB_PATH + DB_NAME)
        if (dbFile.exists()) dbFile.delete()
        copyDataBase()
        mNeedUpdate = false

        runCatching { this@DatabaseHelper.writableDatabase }
            .onSuccess { db -> db.uploadOldUserDataToDB(cur1, cur2) }
            .onFailure { return }
    }

    private fun SQLiteDatabase.uploadOldUserDataToDB(cur1: Cursor, cur2: Cursor) {

        cur1.use {
            while (it.isAfterLast) {
                execSQL("insert into Wanttowatch values (${it.getInt(0)})")
                it.moveToNext()
            }
        }

        cur2.use {
            while (it.isAfterLast) {
                execSQL("insert into Saw values (${it.getInt(0)})")
                it.moveToNext()
            }
        }

        close()
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput = mContext.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        mDataBase = SQLiteDatabase.openDatabase(
            DB_PATH + DB_NAME,
            null,
            SQLiteDatabase.CREATE_IF_NECESSARY
        )
        return mDataBase != null
    }

    @Synchronized
    override fun close() {
        if (mDataBase != null) mDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    companion object {
        private var DB_PATH = ""
        private const val DB_VERSION = 116
    }

    init {
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}
