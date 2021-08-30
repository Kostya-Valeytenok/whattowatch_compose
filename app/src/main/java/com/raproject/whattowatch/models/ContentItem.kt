package com.raproject.whattowatch.models

import android.graphics.Bitmap

data class ContentItem(var key: Int, var image: Bitmap, var name: String, var genres: String, var year: String)
