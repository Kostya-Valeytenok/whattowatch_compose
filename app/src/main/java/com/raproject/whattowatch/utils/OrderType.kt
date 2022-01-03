package com.raproject.whattowatch.utils

enum class OrderType {
    Default, DescendingOrder, AscendingOrder;

    fun by(row: TableRow): String {
        return when (this) {
            Default -> ""
            DescendingOrder -> " ORDER BY ${row.name} DESC"
            AscendingOrder -> " ORDER BY ${row.name} ASC"
        }
    }
}
