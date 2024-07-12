package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SaveHourlyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var saveHourlyForecastUseCase: SaveHourlyForecastUseCase

    @BeforeEach
    fun setUp() {
        saveHourlyForecastUseCase = SaveHourlyForecastUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `invoke() should call saveHourlyForecast on repository with hourlyForecastItem`() =
        runTest {
            val listHourlyForecastItem = listOf(
                HourlyForecastItem(
                    801,
                    "12:00",
                    26
                ),
                HourlyForecastItem(
                    803,
                    "15:00",
                    23
                ),
                HourlyForecastItem(
                    802,
                    "18:00",
                    28
                )
            )

            saveHourlyForecastUseCase(listHourlyForecastItem)

            verify(repository, times(1)).saveHourlyForecast(listHourlyForecastItem)

        }
}