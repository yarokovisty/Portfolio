package com.example.forecastapp.data.repository

import com.example.forecastapp.TestUtils
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.currentweatherdto.Clouds
import com.example.forecastapp.data.network.dto.currentweatherdto.Coord
import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.currentweatherdto.Main
import com.example.forecastapp.data.network.dto.currentweatherdto.Rain
import com.example.forecastapp.data.network.dto.currentweatherdto.Sys
import com.example.forecastapp.data.network.dto.currentweatherdto.Weather
import com.example.forecastapp.data.network.dto.currentweatherdto.Wind
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class ForecastRepositoryCurrentWeatherTest {

    private val gson = Gson()

    private fun readJsonFromFile(fileName: String): String {
        val file = File(javaClass.classLoader!!.getResource(fileName).file)
        return file.readText()
    }

    private val localForecastDataSource = mockk<LocalForecastDataSource>(relaxed = true)
    private val remoteForecastDataSource = mockk<RemoteForecastDataSource>()
    private val mapper = mockk<ForecastMapper>()

    @AfterEach
    fun tearDown() {
        clearMocks(localForecastDataSource, remoteForecastDataSource, mapper)
    }

    @Test
    fun `getCurrentWeather should return success CurrentWeatherItem from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val currentWeatherDTO = TestUtils.readJsonFromFile(
            "mock_current_weather.json",
            CurrentWeatherDTO::class.java
        )
        val currentWeatherItem = CurrentWeatherItem(
            501,
            "moderate rain",
            298,
            1,
            64,
            10000
        )

        coEvery { remoteForecastDataSource.getCurrentWeather(44.34, 10.9) } returns Result.Success(
            currentWeatherDTO
        )
        coEvery { mapper.mapCurrentWeatherDTOtoCurrentWeatherItem(currentWeatherDTO) } returns
                currentWeatherItem

        val expected = Result.Success(currentWeatherItem)
        val actual = repository.getCurrentWeather(44.34, 10.9)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getCurrentWeather should return error CurrentWeatherItem from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { remoteForecastDataSource.getCurrentWeather(44.34, 10.9) } returns Result.Error(
            Exception("Error")
        )

        val expected = Result.Error(Exception("Error"))
        val actual = repository.getCurrentWeather(44.34, 10.9)

        if (actual is Result.Error) {
            Assertions.assertEquals(expected.exception.message, actual.exception.message)
        } else {
            Assertions.fail("Expected and actual results are not of the same type")
        }
    }

    @Test
    fun `getCurrentWeather should return CurrentWeatherItem from DB`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { localForecastDataSource.getCurrentWeather() } returns CurrentWeatherItem(
            800,
            "Sunny",
            31,
            2,
            30,
            10000
        )

        val expected = CurrentWeatherItem(
            800,
            "Sunny",
            31,
            2,
            30,
            10000
        )
        val actual = repository.getCurrentWeather()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getCurrentWeather should return null from DB`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { localForecastDataSource.getCurrentWeather() } returns null

        val expected = null
        val actual = repository.getCurrentWeather()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `saveCurrentWeather should call saveCurrentWeather on localForecastDataSource`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )
        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)

        repository.saveCurrentWeather(currentWeatherItem)

        verify { localForecastDataSource.saveCurrentWeather(currentWeatherItem) }
    }


}