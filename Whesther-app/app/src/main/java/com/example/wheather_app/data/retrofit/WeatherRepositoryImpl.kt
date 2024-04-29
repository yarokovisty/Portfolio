package com.example.wheather_app.data.retrofit

import com.example.wheather_app.data.retrofit.dto.CurrentWeatherDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepositoryImpl {

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

    suspend fun getCurrentWeatherDto(lat: Double, lon: Double): CurrentWeatherDto {
        return weatherApi.getCurrentWeatherDto(lat, lon)
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}