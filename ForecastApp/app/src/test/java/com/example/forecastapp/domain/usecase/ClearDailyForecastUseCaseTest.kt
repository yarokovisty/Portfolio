package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ClearDailyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var clearDailyForecastUseCase: ClearDailyForecastUseCase

    @BeforeEach
    fun setUp() {
        clearDailyForecastUseCase = ClearDailyForecastUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `invoke() should call clearDailyForecast on repository`() = runTest {
        clearDailyForecastUseCase()

        verify(repository, times(1)).clearDailyForecast()
    }
}