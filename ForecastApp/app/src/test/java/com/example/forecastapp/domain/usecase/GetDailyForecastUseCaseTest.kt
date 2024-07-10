package com.example.forecastapp.domain.usecase

import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.repository.ForecastRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetDailyForecastUseCaseTest {

    private val repository = mock<ForecastRepository>()
    private lateinit var getDailyForecastUseCase: GetDailyForecastUseCase
    private lateinit var testListDailyForecastItem: List<DailyForecastItem>

    @BeforeEach
    fun setUp() {
        getDailyForecastUseCase = GetDailyForecastUseCase(repository)
        val testDailyForecastItem1 = DailyForecastItem(
            700,
            "ср, июл 10",
            21
        )
        val testDailyForecastItem2 = DailyForecastItem(
            701,
            "чт, июл 11",
            24
        )
        val testDailyForecastItem3 = DailyForecastItem(
            700,
            "пт, июл 12",
            19
        )
        testListDailyForecastItem = listOf(
            testDailyForecastItem1,
            testDailyForecastItem2,
            testDailyForecastItem3
        )
    }

    @Test
    fun `invoke() should return list of daily forecast from repository local`() = runTest {
        `when`(repository.getDailyForecast()).thenReturn(testListDailyForecastItem)

        val actual = getDailyForecastUseCase()
        val expected = listOf(
            DailyForecastItem(
                700,
                "ср, июл 10",
                21
            ),
            DailyForecastItem(
                701,
                "чт, июл 11",
                24
            ),
            DailyForecastItem(
                700,
                "пт, июл 12",
                19
            )
        )

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `invoke(lon, lat) should return list daily forecast from repository network`() = runTest {
        val resultSuccess = Result.Success(testListDailyForecastItem)

        `when`(repository.getDailyForecast(LON_BARNAUL, LAT_BARNAUL)).thenReturn(resultSuccess)

        val actual = getDailyForecastUseCase(LON_BARNAUL, LAT_BARNAUL)
        val expected = Result.Success(
            listOf(
                DailyForecastItem(
                    700,
                    "ср, июл 10",
                    21
                ),
                DailyForecastItem(
                    701,
                    "чт, июл 11",
                    24
                ),
                DailyForecastItem(
                    700,
                    "пт, июл 12",
                    19
                )
            )
        )

        Assertions.assertEquals(expected, actual)
    }

    private companion object {
        const val LON_BARNAUL = 83.76978
        const val LAT_BARNAUL = 53.35478
    }
}