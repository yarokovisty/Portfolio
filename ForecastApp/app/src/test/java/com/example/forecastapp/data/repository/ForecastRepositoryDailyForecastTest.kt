package com.example.forecastapp.data.repository

import com.example.forecastapp.data.database.model.DailyForecastDbModel
import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.hourlyforecastdto.City
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Clouds
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Coord
import com.example.forecastapp.data.network.dto.hourlyforecastdto.HourlyForecastDTO
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Main
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Rain
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Sys
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Weather
import com.example.forecastapp.data.network.dto.hourlyforecastdto.WeatherData
import com.example.forecastapp.data.network.dto.hourlyforecastdto.Wind
import com.example.forecastapp.domain.entity.DailyForecastItem
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

        val dailyForecastDTO = HourlyForecastDTO(
            cod = "200",
            message = 0,
            cnt = 40,
            list = listOf(
                WeatherData(
                    dt = 1661871600,
                    main = Main(
                        temp = 298.48,
                        feelsLike = 296.98,
                        tempMin = 296.76,
                        tempMax = 297.87,
                        pressure = 1015,
                        seaLevel = 1015,
                        grndLevel = 933,
                        humidity = 69,
                        tempKf = -1.11
                    ),
                    weather = listOf(
                        Weather(
                            id = 800,
                            main = "Sunny",
                            description = "clear",
                            icon = "10d"
                        )
                    ),
                    clouds = Clouds(all = 100),
                    wind = Wind(
                        speed = 1.14,
                        deg = 17,
                        gust = 1.57
                    ),
                    visibility = 10000,
                    pop = 0F,
                    sys = Sys(pod = "d"),
                    dtTxt = "2022-09-04 12:00:00",
                    rain = Rain(threeHour = 0.26)
                )
            ),
            city = City(
                id = 3163858,
                name = "Zocca",
                coord = Coord(
                    lat = 44.34,
                    lon = 10.99
                ),
                country = "IT",
                population = 4593,
                timezone = 7200,
                sunrise = 1661834187,
                sunset = 1661882248
            )
        )
        val dailyForecast = listOf(DailyForecastItem(800, "пт, июл. 13", 296))

        coEvery { remoteForecastDataSource.getDailyForecast(44.34, 10.9) } returns
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

        coEvery { remoteForecastDataSource.getDailyForecast(44.34, 10.9) } returns
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

        coEvery { localForecastDataSource.getDailyForecast() } returns listOf(
            DailyForecastDbModel(800, "пт, июл. 13", 30)
        )
        coEvery {
            mapper.mapDailyForecastDbModelToDailyForecastItem(
                DailyForecastDbModel(800, "пт, июл. 13", 30)
            )
        } returns DailyForecastItem(800, "пт, июл. 13", 30)

        val expected = listOf(DailyForecastItem(800, "пт, июл. 13", 30))
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

        val dailyForecastItem = DailyForecastItem(id = 601, date = "2024-07-13", temp = 20)
        val dailyForecastDbModel = DailyForecastDbModel(id = 601, date = "2024-07-13", temp = 20)

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