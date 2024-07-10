package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ClearHourlyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var clearHourlyForecastUseCase: ClearHourlyForecastUseCase

    @BeforeEach
    fun setUp() {
        clearHourlyForecastUseCase = ClearHourlyForecastUseCase(repository)
    }

    @Test
    fun `invoke() should call clearHourlyForecast on repository`() = runTest {
        clearHourlyForecastUseCase()

        verify(repository, times(1)).clearHourlyForecast()
    }
}