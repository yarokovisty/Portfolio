package com.example.forecastapp.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveCurrentWeatherItem(weatherItem: CurrentWeatherItem) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(weatherItem)
        editor.putString(PREF_KEY, json)
        editor.apply()
    }

    fun getCurrentWeatherItem(): CurrentWeatherItem? {
        val gson = Gson()
        val json = sharedPreferences.getString(PREF_KEY, null)
        return gson.fromJson(json, CurrentWeatherItem::class.java)
    }

    companion object {
        private const val PREF_NAME = "weather_prefs"
        private const val PREF_KEY = "current_weather_item"
    }
}