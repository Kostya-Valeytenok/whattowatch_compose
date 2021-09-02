package com.raproject.whattowatch.repository

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
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
            var maindb: SQLiteDatabase
            maindb = try {
                this.writableDatabase
            } catch (mSQLException: SQLException) {
                throw mSQLException
            }
            val cur1 = maindb.rawQuery("select _Key from Wanttowatch", null)
            val cur2 = maindb.rawQuery("select _Key from Saw", null)
            cur1.moveToFirst()
            cur2.moveToFirst()
            maindb.close()
            val dbFile = File(DB_PATH + DB_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false
            maindb = try {
                this.writableDatabase
            } catch (mSQLException: SQLException) {
                throw mSQLException
            }
            while (!cur1.isAfterLast) {
                maindb.execSQL("insert into Wanttowatch values (" + cur1.getInt(0) + ")")
                cur1.moveToNext()
            }
            while (!cur2.isAfterLast) {
                maindb.execSQL("insert into Saw values (" + cur2.getInt(0) + ")")
                cur2.moveToNext()
            }
            maindb.close()
            cur1.close()
            cur2.close()
        }
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
        if (Build.VERSION.SDK_INT >= 17) DB_PATH =
            context.applicationInfo.dataDir + "/databases/" else DB_PATH =
            "/data/data/" + context.packageName + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}
