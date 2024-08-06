package com.example.randomuser.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    fun formatToDate(dateString: String, outputFormat: String = "dd.MM.yyyy"): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(dateString)

        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

        return date?.let { outputDateFormat.format(it) } ?: ""
    }
}