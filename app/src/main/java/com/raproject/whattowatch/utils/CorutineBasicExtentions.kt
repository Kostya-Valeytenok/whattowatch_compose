package com.raproject.whattowatch.utils

import java.lang.NullPointerException
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports

suspend fun <T> asyncJob(job: suspend () -> T): Deferred<T> = withContext(Dispatchers.Default) {
    return@withContext async { return@async job.invoke() }
}

suspend fun launchJob(job: suspend () -> Unit): Job = withContext(Dispatchers.Default) {
    return@withContext launch { job.invoke() }
}

suspend fun updateUI(job: suspend () -> Unit) = withContext(Dispatchers.Main) {
    job.invoke()
}

suspend fun <T> Result<T>.updateUIIfSuccessFull(updateUIAction: (T) -> Unit): Result<T> {
    onSuccess { result -> updateUI { updateUIAction.invoke(result) } }
    return this
}

fun <T> T?.nullableToResult(): Result<T> {
    return if (this == null) Result.failure(NullPointerException())
    else Result.success(this)
}
