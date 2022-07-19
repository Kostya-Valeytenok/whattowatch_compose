package com.raproject.whattowatch.utils

import java.io.Serializable

enum class Localization(val key: String) : Serializable {
    Russian("ru"), English("en");

    companion object {

        fun createFromKey(key: String): Localization {
            return when (key) {
                "ru" -> Russian
                else -> English
            }
        }
    }
}
