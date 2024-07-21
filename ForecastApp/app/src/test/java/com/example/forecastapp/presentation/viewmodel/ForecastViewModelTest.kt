package com.example.forecastapp.presentation.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.domain.entity.Result
import com.example.forecastapp.domain.usecase.ClearDailyForecastUseCase
import com.example.forecastapp.domain.usecase.ClearHourlyForecastUseCase
import com.example.forecastapp.domain.usecase.GetCurrentWeatherUseCase
import com.example.forecastapp.domain.usecase.GetDailyForecastUseCase
import com.example.forecastapp.domain.usecase.GetHourlyForecastUseCase
import com.example.forecastapp.domain.usecase.SaveCurrentWeatherUseCase
import com.example.forecastapp.domain.usecase.SaveDailyForecastUseCase
import com.example.forecastapp.domain.usecase.SaveHourlyForecastUseCase
import com.example.forecastapp.presentation.state.ForecastState
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifyOrder
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private val saveCurrentWeatherUseCase = mockk<SaveCurrentWeatherUseCase>()
    private val getCurrentWeatherUseCase = mockk<GetCurrentWeatherUseCase>()
    private val saveHourlyForecastUseCase = mockk<SaveHourlyForecastUseCase>()
    private val getHourlyForecastUseCase = mockk<GetHourlyForecastUseCase>()
    private val clearHourlyForecastUseCase = mockk<ClearHourlyForecastUseCase>()
    private val getDailyForecastUseCase = mockk<GetDailyForecastUseCase>()
    private val clearDailyForecastUseCase = mockk<ClearDailyForecastUseCase>()
    private val saveDailyForecastUseCase = mockk<SaveDailyForecastUseCase>()


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })

    }

    @AfterEach
    fun tearDown() {
        clearMocks(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
        )
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
    }


    @Test
    fun `loadDataFromNetwork should return success state`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)
        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase(10.0, 10.0) } returns Result.Success(currentWeatherItem)
        coEvery { getHourlyForecastUseCase(10.0, 10.0) } returns Result.Success(hourlyForecast)
        coEvery { getDailyForecastUseCase(10.0, 10.0) } returns Result.Success(dailyForecast)

        coEvery { saveCurrentWeatherUseCase(currentWeatherItem) } just Runs
        coEvery { saveHourlyForecastUseCase(hourlyForecast) } just Runs
        coEvery { saveDailyForecastUseCase(dailyForecast) } just Runs

        coEvery { clearHourlyForecastUseCase() } just Runs
        coEvery { clearDailyForecastUseCase() } just Runs

        viewModel.loadDataFromNetwork(10.0, 10.0)

        val expected = ForecastState.Success(
            currentWeatherItem,
            hourlyForecast,
            dailyForecast
        )
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromNetwork should return error state when result current is error`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase(10.0, 10.0) } returns
                Result.Error(Exception("Network error"))
        coEvery { getHourlyForecastUseCase(10.0, 10.0) } returns Result.Success(hourlyForecast)
        coEvery { getDailyForecastUseCase(10.0, 10.0) } returns Result.Success(dailyForecast)

        viewModel.loadDataFromNetwork(10.0, 10.0)

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromNetwork should return error state when result hourly is error`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase(10.0, 10.0) } returns Result.Success(currentWeatherItem)
        coEvery { getHourlyForecastUseCase(10.0, 10.0) } returns Result.Error(Exception("Network error"))
        coEvery { getDailyForecastUseCase(10.0, 10.0) } returns Result.Success(dailyForecast)

        viewModel.loadDataFromNetwork(10.0, 10.0)

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromNetwork should return error state when result daily is error`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)
        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))

        coEvery { getCurrentWeatherUseCase(10.0, 10.0) } returns Result.Success(currentWeatherItem)
        coEvery { getHourlyForecastUseCase(10.0, 10.0) } returns Result.Success(hourlyForecast)
        coEvery { getDailyForecastUseCase(10.0, 10.0) } returns Result.Error(Exception("Network error"))

        viewModel.loadDataFromNetwork(10.0, 10.0)

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromDb should return success state`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)
        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase() } returns currentWeatherItem
        coEvery { getHourlyForecastUseCase() } returns hourlyForecast
        coEvery { getDailyForecastUseCase() } returns dailyForecast

        viewModel.loadDataFromDb()

        val expected = ForecastState.Success(
            currentWeatherItem,
            hourlyForecast,
            dailyForecast
        )
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromDb should return error state when current weather is null`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = null
        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase() } returns currentWeatherItem
        coEvery { getHourlyForecastUseCase() } returns hourlyForecast
        coEvery { getDailyForecastUseCase() } returns dailyForecast


        viewModel.loadDataFromDb()

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromDb should return error state when exception is thrown by current`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )
        coEvery { getCurrentWeatherUseCase() } throws RuntimeException("DB error")

        viewModel.loadDataFromDb()

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromDb should return error state when exception is thrown by hourly`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )
        coEvery { getHourlyForecastUseCase() } throws RuntimeException("DB error")

        viewModel.loadDataFromDb()

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromDb should return error state when exception is thrown by daily`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )
        coEvery { getDailyForecastUseCase() } throws RuntimeException("DB error")

        viewModel.loadDataFromDb()

        val expected = ForecastState.Error("Error with Internet")
        val actual = viewModel.forecastState.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `loadDataFromNetwork must cause saveLocal`() = runTest {
        val viewModel = ForecastViewModel(
            saveCurrentWeatherUseCase,
            getCurrentWeatherUseCase,
            saveHourlyForecastUseCase,
            getHourlyForecastUseCase,
            clearHourlyForecastUseCase,
            getDailyForecastUseCase,
            clearDailyForecastUseCase,
            saveDailyForecastUseCase,
        )

        val currentWeatherItem = CurrentWeatherItem(800, "Sunny", 25, 3, 30, 10)
        val hourlyForecast = listOf(HourlyForecastItem(801, "Cloudy", 22))
        val dailyForecast = listOf(DailyForecastItem(601, "Rainy", 20))

        coEvery { getCurrentWeatherUseCase(10.0, 10.0) } returns Result.Success(currentWeatherItem)
        coEvery { getHourlyForecastUseCase(10.0, 10.0) } returns Result.Success(hourlyForecast)
        coEvery { getDailyForecastUseCase(10.0, 10.0) } returns Result.Success(dailyForecast)

        coEvery { saveCurrentWeatherUseCase(currentWeatherItem) } just Runs
        coEvery { saveHourlyForecastUseCase(hourlyForecast) } just Runs
        coEvery { saveDailyForecastUseCase(dailyForecast) } just Runs

        coEvery { clearHourlyForecastUseCase() } just Runs
        coEvery { clearDailyForecastUseCase() } just Runs

        viewModel.loadDataFromNetwork(10.0, 10.0)

        coVerify {
            saveCurrentWeatherUseCase(any())
            saveHourlyForecastUseCase(any())
            saveDailyForecastUseCase(any())
        }
    }
}