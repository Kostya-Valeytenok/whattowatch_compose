package com.raproject.whattowatch.repository

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.raproject.whattowatch.models.OldUserData
import com.raproject.whattowatch.repository.migration.DatabaseMigrationTo120Version
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseHelper(context: Context, private val DB_NAME: String = "content.db") :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), KoinComponent {

    private var DB_PATH = " "

    private var mDataBase: SQLiteDatabase? = null
    private val mContext: Context
    private var mNeedUpdate = false

    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            var oldUserData: OldUserData? = null
            completeForWritable { oldUserData = getOldUserData() }
            oldUserData?.let {
                runCatching {
                    val db =  getWritableDB()
                    db.beginTransaction()
                    completeForWritable { updateDB() }
                    completeForWritable {
                        tryToRestoreUserData(userData = it)
                            .onSuccess { this@DatabaseHelper.mDataBase?.setTransactionSuccessful() }
                    }
                    db.endTransaction()
                }
            }
        }
    }

    private fun SQLiteDatabase.getOldUserData(): OldUserData {
        val favoritesCursor = rawQuery("select * from Favorite", null)
        val sawCursor = rawQuery("select * from Saw", null)
        favoritesCursor.moveToFirst()
        sawCursor.moveToFirst()
        close()
        val oldUserData: OldUserData by inject { parametersOf(favoritesCursor, sawCursor) }
        return oldUserData
    }

    private fun updateDB() {
        val dbFile = File(DB_PATH + DB_NAME)
        if (dbFile.exists()) dbFile.delete()
        copyDataBase()
        mNeedUpdate = false
    }

    private fun SQLiteDatabase.tryToRestoreUserData(userData: OldUserData) = runCatching {
        uploadOldUserDataToDB(
            favoritesData = userData.favoritesCursor,
            sawData = userData.sawCursor
        )
    }

    private fun SQLiteDatabase.uploadOldUserDataToDB(favoritesData: Cursor, sawData: Cursor) {

        favoritesData.use {
            while (!it.isAfterLast) {
                execSQL("insert into Favorite values (${it.getInt(0)})")
                it.moveToNext()
            }
        }

        sawData.use {
            while (!it.isAfterLast) {
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
        if (newVersion > oldVersion) {
            db.runMigration(migration = DatabaseMigrationTo120Version, oldVersion = oldVersion)
                .onFailure {
                    println(it)
                }
            mNeedUpdate = true
        }
    }

    init {
        DB_PATH = context.applicationInfo.dataDir + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }
}
