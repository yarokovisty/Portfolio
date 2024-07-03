package com.example.forecastapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun formatUnixTime(unixTime: Long): String {
    val dv = unixTime * 1000 // перевод в миллисекунды
    val df = Date(dv)
    val dateFormat = SimpleDateFormat("HH:mm")
    return dateFormat.format(df)
}
