package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock


class GetCurrentWeatherUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var testCurrentWeatherItem: CurrentWeatherItem

    @BeforeEach
    fun setUp() {
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)
        testCurrentWeatherItem = CurrentWeatherItem(
            800,
            "Солнечно",
            25,
            3,
            30,
            10
        )
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `invoke() should return current weather from repository local`() {
        `when`(repository.getCurrentWeather()).thenReturn(testCurrentWeatherItem)

        val actual = getCurrentWeatherUseCase()
        val expected = CurrentWeatherItem(
            800,
            "Солнечно",
            25,
            3,
            30,
            10
        )

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `invoke(lon, lat) should return current weather from repository network`() = runTest {
        val resultSuccess = Result.Success(testCurrentWeatherItem)

        `when`(repository.getCurrentWeather(LON_BARNAUL, LAT_BARNAUL)).thenReturn(resultSuccess)

        val actual = getCurrentWeatherUseCase(LON_BARNAUL, LAT_BARNAUL)
        val expected = Result.Success(
            CurrentWeatherItem(
                800,
                "Солнечно",
                25,
                3,
                30,
                10
            )
        )

        Assertions.assertEquals(expected, actual)
    }

    private companion object {
        const val LON_BARNAUL = 83.76978
        const val LAT_BARNAUL = 53.35478
    }
}