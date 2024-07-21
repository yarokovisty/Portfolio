package com.example.forecastapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TIME_FORMAT_HH_MM = "HH:mm"
private const val TIME_FORMAT_EEE_MMM_D = "EEE, MMM d"

fun formatUnixTimeHHMM(unixTime: Long): String {
    val dv = unixTime * 1000 // перевод в миллисекунды
    val df = Date(dv)
    val dateFormat = SimpleDateFormat(TIME_FORMAT_HH_MM)
    return dateFormat.format(df)
}


fun formatIsoDate(unixTime: Long): String {
    val dv = unixTime * 1000
    val df = Date(dv)
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat(TIME_FORMAT_EEE_MMM_D, locale)

    return dateFormat.format(df)
}