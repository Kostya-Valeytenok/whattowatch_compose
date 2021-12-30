package com.raproject.whattowatch.repository

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.raproject.whattowatch.models.OldUserData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class DatabaseHelper(context: Context, private val DB_NAME: String = "content.db") :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), KoinComponent {

    private var DB_PATH = " "

    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    private var mNeedUpdate = false

    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            kotlin.runCatching {
                this.writableDatabase
            }.onSuccess { db ->
                val userData = db.getOldUserData()
                updateDB()
                tryToRestoreUserData(userData = userData)
            }.onFailure { return }
        }
    }

    private fun SQLiteDatabase.getOldUserData(): OldUserData {
        val wantToWatchCursor = rawQuery("select _Key from Wanttowatch", null)
        val sawCursor = rawQuery("select _Key from Saw", null)
        wantToWatchCursor.moveToFirst()
        sawCursor.moveToFirst()
        close()
        val oldUserData: OldUserData by inject { parametersOf(wantToWatchCursor, sawCursor) }
        return oldUserData
    }

    private fun updateDB() {
        val dbFile = File(DB_PATH + DB_NAME)
        if (dbFile.exists()) dbFile.delete()
        copyDataBase()
        mNeedUpdate = false
    }

    private fun tryToRestoreUserData(userData: OldUserData) {
        runCatching { this@DatabaseHelper.writableDatabase }
            .onSuccess { db ->
                db.uploadOldUserDataToDB(
                    wantToWatchData = userData.wantToWatchCursor,
                    sawData = userData.sawCursor
                )
            }
            .onFailure { return }
    }

    private fun SQLiteDatabase.uploadOldUserDataToDB(wantToWatchData: Cursor, sawData: Cursor) {

        wantToWatchData.use {
            while (it.isAfterLast) {
                execSQL("insert into Wanttowatch values (${it.getInt(0)})")
                it.moveToNext()
            }
        }

        sawData.use {
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

    init {
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}
