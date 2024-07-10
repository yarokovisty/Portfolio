package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SaveDailyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var saveDailyForecastUseCase: SaveDailyForecastUseCase

    @BeforeEach
    fun setUp() {
        saveDailyForecastUseCase = SaveDailyForecastUseCase(repository)
    }

    @Test
    fun `invoke() should call saveDailyForecast on repository with dailyForecastItem`() =
        runTest {
            val listDailyForecastItem = listOf(
                DailyForecastItem(
                    500,
                    "ср, 10 июл",
                    19
                ),
                DailyForecastItem(
                    501,
                    "чт, 11 июл",
                    17
                ),
                DailyForecastItem(
                    503,
                    "пт, 12 июл",
                    20
                )
            )

            saveDailyForecastUseCase(listDailyForecastItem)

            verify(repository, times(1)).saveDailyForecast(listDailyForecastItem)
        }
}