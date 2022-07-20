package com.raproject.whattowatch.utils

object AppErrorHandler {

    fun Throwable.logError() {
        // TODO implement Analytics
        println(this)
    }

    fun logMessage(text: String) {
        println(text)
    }
}