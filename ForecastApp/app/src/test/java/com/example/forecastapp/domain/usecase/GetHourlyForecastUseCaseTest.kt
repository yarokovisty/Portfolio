package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.HourlyForecastItem
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

class GetHourlyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var getHourlyForecastUseCase: GetHourlyForecastUseCase
    private lateinit var testListHourlyForecastItem: List<HourlyForecastItem>

    @BeforeEach
    fun setUp() {
        getHourlyForecastUseCase = GetHourlyForecastUseCase(repository)
        val testHourlyForecastItem1 = HourlyForecastItem(
            800,
            "01:00",
            16
        )
        val testHourlyForecastItem2 = HourlyForecastItem(
            800,
            "04:00",
            14
        )
        val testHourlyForecastItem3 = HourlyForecastItem(
            800,
            "07:00",
            14
        )
        testListHourlyForecastItem = listOf(
            testHourlyForecastItem1,
            testHourlyForecastItem2,
            testHourlyForecastItem3
        )
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `invoke() should return list of hourly forecast from repository local`() = runTest {
        `when`(repository.getHourlyForecast()).thenReturn(testListHourlyForecastItem)

        val actual = getHourlyForecastUseCase()
        val expected = listOf(
            HourlyForecastItem(
                800,
                "01:00",
                16
            ),
            HourlyForecastItem(
                800,
                "04:00",
                14
            ),
            HourlyForecastItem(
                800,
                "07:00",
                14
            )
        )

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `invoke(lon, lat) should return list of hourly forecast from repository network`() =
        runTest {
            val resultSuccess = Result.Success(testListHourlyForecastItem)

            `when`(repository.getHourlyForecast(LON_BARNAUL, LAT_BARNAUL)).thenReturn(resultSuccess)

            val actual = getHourlyForecastUseCase(LON_BARNAUL, LAT_BARNAUL)
            val expected = Result.Success(
                listOf(
                    HourlyForecastItem(
                        800,
                        "01:00",
                        16
                    ),
                    HourlyForecastItem(
                        800,
                        "04:00",
                        14
                    ),
                    HourlyForecastItem(
                        800,
                        "07:00",
                        14
                    )
                )
            )

            Assertions.assertEquals(expected, actual)
        }

    @Test
    fun `invoke(lon, lat) should return error from repository network`() =
        runTest {
            val exception = Exception("Network error")
            val resultError = Result.Error(exception)

            `when`(repository.getHourlyForecast(LON_BARNAUL, LAT_BARNAUL)).thenReturn(resultError)

            val result = getHourlyForecastUseCase(LON_BARNAUL, LAT_BARNAUL)

            Assertions.assertTrue(result is Result.Error)
            Assertions.assertTrue((result as Result.Error).exception.message == "Network error")
        }


    private companion object {
        const val LON_BARNAUL = 83.76978
        const val LAT_BARNAUL = 53.35478
    }

}