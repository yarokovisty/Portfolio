package com.example.forecastapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.domain.entity.HourlyForecastItem


@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHourlyForecast(listHourlyForecastItem: List<HourlyForecastDbModel>)

    @Query("SELECT * FROM hourly_forecast")
    suspend fun getHourlyForecast(): List<HourlyForecastDbModel>

    @Query("DELETE FROM hourly_forecast")
    suspend fun clearHourlyForecast()

}