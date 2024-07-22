package com.example.forecastapp.data.repository

import com.example.forecastapp.TestUtils
import com.example.forecastapp.data.database.model.DailyForecastDbModel
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForecastRepositoryDailyForecastTest {

    private val localForecastDataSource = mockk<LocalForecastDataSource>(relaxed = true)
    private val remoteForecastDataSource = mockk<RemoteForecastDataSource>()
    private val mapper = mockk<ForecastMapper>()

    @AfterEach
    fun tearDown() {
        clearMocks(localForecastDataSource, remoteForecastDataSource, mapper)
    }

    @Test
    fun `getDailyForecast should return success dailyForecast from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val dailyForecastDTO = TestUtils.readJsonFromFile(
            "mock_hourly_forecast.json",
            HourlyForecastDTO::class.java
        )
        val dailyForecast = listOf(TestUtils.readJsonFromFile(
            "mock_daily_forecast_item.json",
            DailyForecastItem::class.java
        ))

        coEvery { remoteForecastDataSource.getHourlyForecast(44.34, 10.9) } returns
                Result.Success(dailyForecastDTO)
        coEvery { mapper.mapHourlyForecastDTOtoDaileForecastItem(dailyForecastDTO.list) } returns
                dailyForecast

        val expected = Result.Success(dailyForecast)
        val actual = repository.getDailyForecast(44.34, 10.9)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getDailyForecast should return error from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { remoteForecastDataSource.getHourlyForecast(44.34, 10.9) } returns
                Result.Error(Exception("Error"))

        val expected = Result.Error(Exception("Error"))
        val actual = repository.getDailyForecast(44.34, 10.9)

        if (actual is Result.Error) {
            Assertions.assertEquals(expected.exception.message, actual.exception.message)
        } else {
            Assertions.fail("Expected and actual results are not of the same type")
        }
    }

    @Test
    fun `getDailyForecast should return DailyForecast from DB`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val dailyForecastItem = TestUtils.readJsonFromFile(
            "mock_daily_forecast_item.json",
            DailyForecastItem::class.java
        )
        val dailyForecastDbModel = TestUtils.readJsonFromFile(
            "mock_daily_forecast_db_model.json",
            DailyForecastDbModel::class.java
        )

        coEvery { localForecastDataSource.getDailyForecast() } returns listOf(
            dailyForecastDbModel
        )
        coEvery {
            mapper.mapDailyForecastDbModelToDailyForecastItem(
                dailyForecastDbModel
            )
        } returns dailyForecastItem

        val expected = listOf(dailyForecastItem)
        val actual = repository.getDailyForecast()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `saveDailyForecast should call saveDailyForecast on localForecastDataSource with mapped data`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val dailyForecastItem = TestUtils.readJsonFromFile(
            "mock_daily_forecast_item.json",
            DailyForecastItem::class.java
        )
        val dailyForecastDbModel = TestUtils.readJsonFromFile(
            "mock_daily_forecast_db_model.json",
            DailyForecastDbModel::class.java
        )

        val listDailyForecastItem = listOf(dailyForecastItem)
        val listDailyForecastDbModel = listOf(dailyForecastDbModel)

        every { mapper.mapDailyForecastItemToDailyForecastDbModel(dailyForecastItem) } returns dailyForecastDbModel

        repository.saveDailyForecast(listDailyForecastItem)

        coVerify { localForecastDataSource.saveDailyForecast(listDailyForecastDbModel) }
    }

    @Test
    fun `clearDailyForecast should call clearDailyForecast on localForecastDataSource`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        repository.clearDailyForecast()

        coVerify { localForecastDataSource.clearDailyForecast() }
    }

}