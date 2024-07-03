package com.example.forecastapp.util

import com.example.forecastapp.R

object WeatherUtils {
    private val THUNDERSTORM = 200..232
    private val RAIN = 300..531
    private val SNOW = 600..622
    private val MIST = 701..781
    private const val CLEAR = 800
    private val CLOUDS = 801..804

    fun getBackgroundColor(id: Int): Pair<Int, Int> =
        when(id) {
            in THUNDERSTORM -> Pair(R.color.primary_bg_thunderstorm, R.color.secondary_bg_thunderstorm)
            in RAIN -> Pair(R.color.primary_bg_rainy, R.color.secondary_bg_rainy)
            in SNOW -> Pair(R.color.primary_bg_snow, R.color.secondary_bg_snow)
            in MIST -> Pair(R.color.primary_bg_mist, R.color.secondary_bg_mist)
            CLEAR -> Pair(R.color.primary_bg_clear, R.color.secondary_bg_clear)
            in CLOUDS -> Pair(R.color.primary_bg_cloudy, R.color.secondary_bg_cloudy)
            else -> throw IllegalArgumentException("Invalid weather id: $id")
        }

    fun getIconWeather(id: Int): Int =
        when(id) {
            in THUNDERSTORM -> R.drawable.icon_thunderstorm
            in RAIN -> R.drawable.icon_rainy
            in SNOW -> R.drawable.icon_snow
            in MIST -> R.drawable.icon_mist
            CLEAR -> R.drawable.icon_clear
            in CLOUDS -> R.drawable.icon_cloud
            else -> throw IllegalArgumentException("Invalid weather id: $id")
        }
}