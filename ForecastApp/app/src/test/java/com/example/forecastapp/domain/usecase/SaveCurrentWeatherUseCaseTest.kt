package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SaveCurrentWeatherUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var saveCurrentWeatherUseCase: SaveCurrentWeatherUseCase

    @BeforeEach
    fun setUp() {
        saveCurrentWeatherUseCase = SaveCurrentWeatherUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `invoke() should call saveCurrentWeather on repository with currentWeatherItem`() = runTest {
        val currentWeatherItem = CurrentWeatherItem(
            800,
            "Sunny",
            25,
            3,
            30,
            10
        )

        saveCurrentWeatherUseCase(currentWeatherItem)

        verify(repository, times(1)).saveCurrentWeather(currentWeatherItem)
    }
}