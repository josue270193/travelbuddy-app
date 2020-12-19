package com.app.josuelopez.travelbuddy.utils

import android.annotation.SuppressLint

internal object StringUtil {

    fun toSnakeCase(value: String): String {
        return value.replace("\\s".toRegex(), "_")
    }

    @SuppressLint("DefaultLocale")
    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.toLowerCase().capitalize() }
}