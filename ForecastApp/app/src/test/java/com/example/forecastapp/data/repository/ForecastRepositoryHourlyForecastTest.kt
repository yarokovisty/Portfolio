package com.example.forecastapp.data.repository

import com.example.forecastapp.TestUtils
import com.example.forecastapp.data.database.model.HourlyForecastDbModel
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.domain.entity.HourlyForecastItem
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

class ForecastRepositoryHourlyForecastTest {

    private val localForecastDataSource = mockk<LocalForecastDataSource>(relaxed = true)
    private val remoteForecastDataSource = mockk<RemoteForecastDataSource>()
    private val mapper = mockk<ForecastMapper>()

    @AfterEach
    fun tearDown() {
        clearMocks(localForecastDataSource, remoteForecastDataSource, mapper)
    }


    @Test
    fun `getHourlyForecast should return success HourlyForecastItem from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )
        val hourlyForecastDTO = TestUtils.readJsonFromFile(
            "mock_hourly_forecast.json",
            HourlyForecastDTO::class.java
        )
        val hourlyForecast = listOf(
            TestUtils.readJsonFromFile(
                "mock_hourly_forecast_item.json",
                HourlyForecastItem::class.java
            )
        )

        coEvery { remoteForecastDataSource.getHourlyForecast(44.34, 10.9) } returns
                Result.Success(hourlyForecastDTO)
        coEvery { mapper.mapHourlyForecastDTOtoHourlyForecastItem(hourlyForecastDTO.list) } returns
                hourlyForecast

        val expected = Result.Success(hourlyForecast)
        val actual = repository.getHourlyForecast(44.34, 10.9)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getHourlyForecast should return error from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { remoteForecastDataSource.getHourlyForecast(44.34, 10.9) } returns
                Result.Error(Exception("Error"))

        val expected = Result.Error(Exception("Error"))
        val actual = repository.getHourlyForecast(44.34, 10.9)

        if (actual is Result.Error) {
            Assertions.assertEquals(expected.exception.message, actual.exception.message)
        } else {
            Assertions.fail("Expected and actual results are not of the same type")
        }
    }

    @Test
    fun `getHourlyForecast should return HourlyForecast from DB`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val hourlyForecastItem = TestUtils.readJsonFromFile(
            "mock_hourly_forecast_item.json",
            HourlyForecastItem::class.java
        )
        val hourlyForecastDbModel = TestUtils.readJsonFromFile(
            "mock_hourly_forecast_db_model.json",
            HourlyForecastDbModel::class.java
        )

        coEvery { localForecastDataSource.getHourlyForecast() } returns listOf(
            hourlyForecastDbModel
        )
        coEvery {
            mapper.mapHourlyForecastDbModelToHourlyForecastItem(
                hourlyForecastDbModel
            )
        } returns hourlyForecastItem

        val expected = listOf(
            TestUtils.readJsonFromFile(
                "mock_hourly_forecast_item.json",
                HourlyForecastItem::class.java
            )
        )
        val actual = repository.getHourlyForecast()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `saveHourlyForecast should map and save hourly forecast items`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val hourlyForecastItem = TestUtils.readJsonFromFile(
            "mock_hourly_forecast_item.json",
            HourlyForecastItem::class.java
        )
        val hourlyForecastDbModel = TestUtils.readJsonFromFile(
            "mock_hourly_forecast_db_model.json",
            HourlyForecastDbModel::class.java
        )

        val hourlyForecastItems = listOf(
            hourlyForecastItem,
            hourlyForecastItem
        )

        val dbModels = listOf(
            hourlyForecastDbModel,
            hourlyForecastDbModel
        )

        every { mapper.mapHourlyForecastItemToHourlyForecastDbModel(hourlyForecastItems[0]) } returns dbModels[0]
        every { mapper.mapHourlyForecastItemToHourlyForecastDbModel(hourlyForecastItems[1]) } returns dbModels[1]

        repository.saveHourlyForecast(hourlyForecastItems)

        coVerify { localForecastDataSource.saveHourlyForecast(dbModels) }
    }

    @Test
    fun `clearHourlyForecast should call clearHourlyForecast on localForecastDataSource`() =
        runTest {
            val repository = ForecastRepositoryImpl(
                localForecastDataSource,
                remoteForecastDataSource,
                mapper
            )

            repository.clearHourlyForecast()
            coVerify { localForecastDataSource.clearHourlyForecast() }
        }
}