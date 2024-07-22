package com.example.forecastapp.data.repository

import com.example.forecastapp.TestUtils
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForecastRepositoryCurrentWeatherTest {


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
        val currentWeatherItem = TestUtils.readJsonFromFile(
            "mock_current_weather_item.json",
            CurrentWeatherItem::class.java
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

        val currentWeatherItem = TestUtils.readJsonFromFile(
            "mock_current_weather_item.json",
            CurrentWeatherItem::class.java
        )

        coEvery { localForecastDataSource.getCurrentWeather() } returns currentWeatherItem

        val expected = TestUtils.readJsonFromFile(
            "mock_current_weather_item.json",
            CurrentWeatherItem::class.java
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
        val currentWeatherItem = TestUtils.readJsonFromFile(
            "mock_current_weather_item.json",
            CurrentWeatherItem::class.java
        )

        repository.saveCurrentWeather(currentWeatherItem)

        verify { localForecastDataSource.saveCurrentWeather(currentWeatherItem) }
    }


}