package com.example.wheather_app.data.retrofit

import com.example.wheather_app.data.mappers.toCurrentWeatherItem
import com.example.wheather_app.data.mappers.toHourlyWeatherItem
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.entity.HourlyWeatherItem
import com.example.wheather_app.domain.repository.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepositoryImpl : WeatherRepository {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherItem {
        return weatherApi.getCurrentWeatherDto(lat, lon).toCurrentWeatherItem()
    }

    override suspend fun getHourlyWeather(lat: Double, lon: Double): List<HourlyWeatherItem> {
        return weatherApi.getHourlyWeatherDto(lat, lon).list.map { it.toHourlyWeatherItem() }
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}