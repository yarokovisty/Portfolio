package com.example.forecastapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.R
import com.example.forecastapp.databinding.ActivityMainBinding
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.presentation.ForecastApplication
import com.example.forecastapp.presentation.state.ForecastState
import com.example.forecastapp.presentation.viewmodel.ForecastViewModel
import com.example.forecastapp.presentation.viewmodel.ViewModelFactory
import com.example.forecastapp.util.WeatherUtils
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ForecastViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ForecastViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData(83.7636, 53.3606)
    }

    private fun initComponent() {
        val component = (applicationContext as ForecastApplication).appComponent
        component.inject(this)
    }

    private fun observeViewModel() {
        viewModel.forecastState.observe(this, ::renderState)
    }

    private fun renderState(state: ForecastState) {

        when(state) {
            ForecastState.Initial -> {}
            ForecastState.Loading -> renderLoading()
            is ForecastState.Success -> renderSuccess(
                state.currentWeatherItem,
                state.listHourlyForecastItem
            )
            is ForecastState.Error -> renderError(state.errorMessage)
        }
    }

    private fun renderLoading() {

    }

    private fun renderSuccess(
        currentWeather: CurrentWeatherItem,
        listHourlyForecastItem: List<HourlyForecastItem>
    ) {

        renderCurrentWeather(currentWeather)
        Log.i("MyLog", listHourlyForecastItem.toString())
    }

    private fun renderError(error: String) {

    }

    private fun renderCurrentWeather(currentWeather: CurrentWeatherItem) = with(binding) {
        tvTempCurrentWeather.text = getString(R.string.template_temperature, currentWeather.temp.toString())
        tvDescriptionCurrentWeather.text = currentWeather.description
        tvWindValue.text = getString(R.string.template_wind, currentWeather.wind.toString())
        tvHumidityValue.text = getString(R.string.template_humidity, currentWeather.humidity.toString())
        tvVisibilityValue.text = getString(
            R.string.template_visibility, currentWeather.visibility.toString()
        )

        val (primaryBackgroundColor, secondaryBackgroundColor) =
            WeatherUtils.getBackgroundColor(currentWeather.id)

        setPrimaryBackgroundColor(primaryBackgroundColor)
        setSecondaryBackgroundColor(secondaryBackgroundColor)
        setIconCurrentWeather(currentWeather.id)
    }

    private fun setPrimaryBackgroundColor(primaryBackgroundColor: Int) {
        binding.layoutMain.setBackgroundColor(
            ContextCompat.getColor(this, primaryBackgroundColor)
        )
        window.statusBarColor = ContextCompat.getColor(this, primaryBackgroundColor)
    }

    private fun setSecondaryBackgroundColor(secondaryBackgroundColor: Int) {
        binding.cardViewCurrentWeather.setCardBackgroundColor(
            ContextCompat.getColor(this, secondaryBackgroundColor)
        )
    }

    private fun setIconCurrentWeather(idCurrentWeather: Int) {
        binding.imgCurrentWeather.setImageResource(WeatherUtils.getIconWeather(idCurrentWeather))
    }
}