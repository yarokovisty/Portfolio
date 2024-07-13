package com.example.forecastapp.data.repository

import com.example.forecastapp.data.datasource.LocalForecastDataSource
import com.example.forecastapp.data.datasource.RemoteForecastDataSource
import com.example.forecastapp.data.mapper.ForecastMapper
import com.example.forecastapp.data.network.dto.currentweatherdto.Clouds
import com.example.forecastapp.data.network.dto.currentweatherdto.Coord
import com.example.forecastapp.data.network.dto.currentweatherdto.CurrentWeatherDTO
import com.example.forecastapp.data.network.dto.currentweatherdto.Main
import com.example.forecastapp.data.network.dto.currentweatherdto.Rain
import com.example.forecastapp.data.network.dto.currentweatherdto.Sys
import com.example.forecastapp.data.network.dto.currentweatherdto.Weather
import com.example.forecastapp.data.network.dto.currentweatherdto.Wind
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ForecastRepositoryCurrentWeatherTest {

    private val localForecastDataSource = mockk<LocalForecastDataSource>(relaxed = true)
    private val remoteForecastDataSource = mockk<RemoteForecastDataSource>()
    private val mapper = mockk<ForecastMapper>()

    @AfterEach
    fun tearDown() {
        clearMocks(localForecastDataSource, remoteForecastDataSource, mapper)
    }

    @Test
    fun `getCurrentWeather should return success CurrentWeatherItem from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        val currentWeatherDTO = CurrentWeatherDTO(
            coord = Coord(lat = 10.99, lon = 44.34),
            weather = listOf(
                Weather(id = 501, main = "Rain", description = "moderate rain", icon = "10d")
            ),
            base = "stations",
            main = Main(
                temp = 298.48,
                feels_like = 298.74,
                temp_min = 297.56,
                temp_max = 300.05,
                pressure = 1015,
                humidity = 64,
                sea_level = 1014,
                grnd_level = 933
            ),
            visibility = 10000,
            wind = Wind(speed = 0.62, deg = 349, gust = 1.18),
            rain = Rain(`1h` = 3.16),
            clouds = Clouds(all = 100),
            dt = 1661870592,
            sys = Sys(
                type = 2,
                id = 2075663,
                country = "IT",
                sunrise = 1661834187,
                sunset = 1661882248
            ),
            timezone = 7200,
            id = 1661882248,
            name = "Zocca",
            cod = 200
        )
        val currentWeatherItem = CurrentWeatherItem(
            501,
            "moderate rain",
            298,
            1,
            64,
            10000
        )

        coEvery { remoteForecastDataSource.getCurrentWeather(44.34, 10.9) } returns Result.Success(
            currentWeatherDTO
        )
        coEvery { mapper.mapCurrentWeatherDTOtoCurrentWeatherItem(currentWeatherDTO) } returns
                currentWeatherItem

        val expected = Result.Success(currentWeatherItem)
        val actual = repository.getCurrentWeather(44.34, 10.9)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getCurrentWeather should return error CurrentWeatherItem from network`() = runTest {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { remoteForecastDataSource.getCurrentWeather(44.34, 10.9) } returns Result.Error(
            Exception("Error")
        )

        val expected = Result.Error(Exception("Error"))
        val actual = repository.getCurrentWeather(44.34, 10.9)

        if (actual is Result.Error) {
            Assertions.assertEquals(expected.exception.message, actual.exception.message)
        } else {
            Assertions.fail("Expected and actual results are not of the same type")
        }
    }

    @Test
    fun `getCurrentWeather should return CurrentWeatherItem from DB`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { localForecastDataSource.getCurrentWeather() } returns CurrentWeatherItem(
            800,
            "Sunny",
            31,
            2,
            30,
            10000
        )

        val expected = CurrentWeatherItem(
            800,
            "Sunny",
            31,
            2,
            30,
            10000
        )
        val actual = repository.getCurrentWeather()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getCurrentWeather should return null from DB`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )

        coEvery { localForecastDataSource.getCurrentWeather() } returns null

        val expected = null
        val actual = repository.getCurrentWeather()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `saveCurrentWeather should call saveCurrentWeather on localForecastDataSource`() {
        val repository = ForecastRepositoryImpl(
            localForecastDataSource,
            remoteForecastDataSource,
            mapper
        )
        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)

        repository.saveCurrentWeather(currentWeatherItem)

        verify { localForecastDataSource.saveCurrentWeather(currentWeatherItem) }
    }


}