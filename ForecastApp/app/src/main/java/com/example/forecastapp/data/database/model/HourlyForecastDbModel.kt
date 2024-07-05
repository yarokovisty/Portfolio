package com.example.forecastapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_forecast")
data class HourlyForecastDbModel(
    val id: Int,
    @PrimaryKey
    val time: String,
    val temp: Int
)
