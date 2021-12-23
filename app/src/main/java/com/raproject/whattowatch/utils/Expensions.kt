package com.raproject.whattowatch.utils

inline fun <T> Collection<T>.forEachIndexedWithLastMarker(
    action: (index: Int, T, isLast: Boolean) -> Unit
) {
    forEachIndexed { index, t ->
        action.invoke(index, t, (size - 1 == index))
    }
}
