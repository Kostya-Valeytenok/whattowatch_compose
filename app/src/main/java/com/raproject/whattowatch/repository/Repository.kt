package com.raproject.whattowatch.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

open class Repository {

    protected suspend fun <T> runRequest(request: suspend () -> T): Deferred<T> = withContext(
        Dispatchers.Default) {
        return@withContext async { request.invoke() }
    }
}