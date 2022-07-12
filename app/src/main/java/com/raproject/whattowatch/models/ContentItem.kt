package com.raproject.whattowatch.models

import android.graphics.Bitmap

data class ContentItem(
    var key: Int,
    var image: Bitmap?,
    var name: String,
    var year: String,
    var genres: String,
    var rating: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContentItem) return false

        if (key != other.key) return false
        if ((image != null && other.image == null) || (image == null && other.image != null)) return false
        if (name != other.name) return false
        if (year != other.year) return false
        if (genres != other.genres) return false
        if (rating != other.rating) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key
        result = if(image != null) 31 * result else result
        result = 31 * result + name.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + genres.hashCode()
        result = 31 * result + (rating?.hashCode() ?: 0)
        return result
    }
}
