package com.example.forecastapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_forecast")
data class DailyForecastDbModel(
    val id: Int,
    @PrimaryKey
    val date: String,
    val temp: Int
)