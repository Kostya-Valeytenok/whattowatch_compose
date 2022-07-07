package com.raproject.whattowatch.repository.request

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.raproject.whattowatch.utils.DBTable
import com.raproject.whattowatch.utils.Localization

class GetTop100(params:Bundle) : GetRequest<String>() {


    companion object {
        private const val LOCALIZATION_KEY = "LOCALIZATION_KEY"
        private const val ORDER_COMMAND = "ORDER_TYPE"

        fun createParams(
            localization: Localization,
            orderCommand: String
        ): Bundle = Bundle().apply {
            putString(ORDER_COMMAND, orderCommand)
            putSerializable(LOCALIZATION_KEY, localization)
        }
    }

    private val localization: Localization by lazy { params.getSerializable(LOCALIZATION_KEY) as Localization }
    private val orderCommand: String by lazy { params.getString(ORDER_COMMAND, "") }

    override suspend fun SQLiteDatabase.runRequest(): Result<String> = runCatching {
        val contentTable = mainTable.tableName(localization)
        safeGetRequest("select ${DBTable.MainTable.key} from $contentTable$orderCommand LIMIT 100") {
            val idList = mutableListOf<String>()
            while (!isAfterLast) {
                idList.add(getString(0))
                toNext()
            }
            idList.joinToString()
        }.getOrThrow()
    }

}