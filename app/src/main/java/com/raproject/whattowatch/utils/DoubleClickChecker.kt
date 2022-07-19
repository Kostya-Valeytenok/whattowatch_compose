package com.raproject.whattowatch.utils

import android.view.ViewConfiguration

class DoubleClickChecker(private val timeoutMillis: Long = ViewConfiguration.getDoubleTapTimeout().toLong()) {
    val isDoubleClicked: Boolean
        get() {
            val clickTime = System.currentTimeMillis()
            val clickDifference = clickTime - lastBackButtonClickedTime

            try {
                return clickDifference <= timeoutMillis
            } finally {
                lastBackButtonClickedTime = clickTime
            }
        }


    private var lastBackButtonClickedTime = 0L
}