package com.example.forecastapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun formatUnixTimeHHMM(unixTime: Long): String {
    val dv = unixTime * 1000 // перевод в миллисекунды
    val df = Date(dv)
    val dateFormat = SimpleDateFormat("HH:mm")
    return dateFormat.format(df)
}


@SuppressLint("SimpleDateFormat")
fun formatIsoDate(unixTime: Long): String {
    val dv = unixTime * 1000
    val df = Date(dv)
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat("EEE, MMM d", locale)

    return dateFormat.format(df)
}