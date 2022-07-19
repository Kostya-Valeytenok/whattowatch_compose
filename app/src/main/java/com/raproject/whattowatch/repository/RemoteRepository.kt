package com.raproject.whattowatch.repository

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RemoteRepository {

    private val firebaseStorage = Firebase.storage

    suspend fun getPosterURLById(storageURL: String) = withContext(Dispatchers.Default) {
        val result = CompletableDeferred<Uri>()
        val downloadTask = firebaseStorage.reference.child(storageURL).downloadUrl
        downloadTask.continueWith { task ->
            if (task.isSuccessful.not()) {
                "".toUri()
            } else downloadTask.result
        }
        downloadTask
            .addOnCompleteListener { result.complete(downloadTask.result) }
            .addOnFailureListener { result.complete(downloadTask.result) }

        result
    }
}