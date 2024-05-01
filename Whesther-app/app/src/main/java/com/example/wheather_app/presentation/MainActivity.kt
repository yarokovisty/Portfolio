package com.example.wheather_app.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.example.wheather_app.R
import com.example.wheather_app.databinding.ActivityMainBinding
import com.example.wheather_app.domain.entity.CurrentWeatherItem
import com.example.wheather_app.domain.entity.HourlyWeatherItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: DailyForecastAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setupRecyclerView()
        loadContent()
        observerViewModel()

    }

    override fun onStart() {
        super.onStart()
        getCurrentLocation()
    }

    private fun observerViewModel() {
        viewModel.currentWeatherItem.observe(this) { weather ->
            displayCurrentWeather(weather)
        }
        viewModel.hourlyWeatherList.observe(this) {forecast ->
            displayHourlyForecast(forecast)
        }
        viewModel.dailyForecastList.observe(this) {forecast ->
            adapter.dailyForecastList = forecast
        }
        viewModel.typeCurrentWeather.observe(this) {type ->
            displayCurrentTypeWeather(type)
        }
        viewModel.isLoading.observe(this) { isLoading ->
            if (!isLoading) showContent()
        }
        viewModel.error.observe(this) { errorMessage ->
            errorContent(errorMessage)
        }
    }

    private fun setupRecyclerView() {
        adapter = DailyForecastAdapter()
        binding.rvDailyForecastList.adapter = adapter
    }

    private fun showContent() {
        with(binding) {
            pbLoadWeather.isVisible = false
            errorScreen.isVisible = false
            contentScreen.isVisible = true
        }
    }

    private fun loadContent() {
        with(binding) {
            pbLoadWeather.isVisible = true
            errorScreen.isVisible = false
            contentScreen.isVisible = false
        }
    }

    private fun errorContent(error: String) {
        with(binding) {
            pbLoadWeather.isVisible = false
            errorScreen.isVisible = true
            contentScreen.isVisible = false
        }
    }

    private fun setColorCardView(color: Int) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.card_background)
        drawable?.setTint(ContextCompat.getColor(this, color))

        binding.contentScreen.forEach { view ->
            if (view is CardView) {
                view.background = drawable
            }
        }
    }

    private fun getIconCurrentWeather(type: Int): Int {
        return when(type) {
            in THUNDERSTORM -> R.drawable.icon_thunderstorm
            in DRIZZLE -> R.drawable.icon_drizzle
            in RAIN -> R.drawable.icon_rain
            in SNOW -> R.drawable.icon_snow
            in ATMOSPHERE -> R.drawable.icon_atmosphere
            CLEAR -> R.drawable.icon_clear
            in CLOUDS -> R.drawable.icon_cloud
            else -> throw RuntimeException("There isn't such type ${type}")
        }
    }

    private fun getBackgroundColor(type: Int): Int {
        return when(type) {
            in THUNDERSTORM -> R.color.stormy_background
            in DRIZZLE -> R.color.rainy_background
            in RAIN -> R.color.rainy_background
            in SNOW -> R.color.snow_background
            in ATMOSPHERE -> R.color.cloudy_background
            CLEAR -> R.color.clear_background
            in CLOUDS -> R.color.cloudy_background
            else -> throw RuntimeException("There isn't such type ${type}")
        }
    }

    private fun getCardViewColor(type: Int): Int {
        return when(type) {
            in THUNDERSTORM -> R.color.stormy_card_view
            in DRIZZLE -> R.color.rainy_card_view
            in RAIN -> R.color.rainy_card_view
            in SNOW -> R.color.snow_card_view
            in ATMOSPHERE -> R.color.cloudy_background
            CLEAR -> R.color.clear_card_view
            in CLOUDS -> R.color.cloudy_card_view
            else -> throw RuntimeException("There isn't such type ${type}")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, R.string.denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location = task.result

                    if (location == null) {
                        Toast.makeText(this, getString(R.string.null_received), Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.getCurrentWeather(location.latitude, location.longitude)
                        viewModel.getHourlyWeather(location.latitude, location.longitude, getCurrentDateTime())
                    }
                }
            } else {
                newIntentLocationSettings()
            }
        } else {
            requestPermission()
        }


    }

    private fun checkPermission(): Boolean {

        return (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            ARR_PERMISSION,
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun newIntentLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun roundTemp(temp: Double) = temp.toInt().toString()

    private fun mapperMToKm(num: Int) = (num / KM).toString()

    private fun templateTime(time: String) = time.split(" ")[1].subSequence(0..4)

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }

    private fun displayCurrentWeather(weather: CurrentWeatherItem) {
        with(binding) {
            tvCurrentTypeWeather.text = weather.description
            tvCurrentTemp.text = getString( R.string.temperature, roundTemp(weather.temp))
            tvParamWind.text = getString(R.string.param_wind, weather.wind.toString())
            tvParamHumidity.text = getString(R.string.param_humidity, weather.humidity.toString())
            tvParamVisibility.text = getString(R.string.param_visibility, mapperMToKm(weather.visibility))
        }
        setColorCardView(getCardViewColor(weather.main))
    }

    private fun displayCurrentTypeWeather(type: Int) {
        binding.imgCurrentTypeWeather.setImageResource(getIconCurrentWeather(type))
        binding.mainContainer.setBackgroundResource(getBackgroundColor(type))
    }

    private fun displayHourlyForecast(forecast: List<HourlyWeatherItem>) {
        with(binding) {
            tvFirstWeatherTime.text = templateTime(forecast[0].time)
            tvSecondWeatherTime.text = templateTime(forecast[1].time)
            tvThirdWeatherTime.text = templateTime(forecast[2].time)
            tvFourthWeatherTime.text = templateTime(forecast[3].time)
            tvFifthWeatherTime.text = templateTime(forecast[4].time)
            imgFirstWeatherType.setImageResource(getIconCurrentWeather(forecast[0].type))
            imgSecondWeatherType.setImageResource(getIconCurrentWeather(forecast[1].type))
            imgThirdWeatherType.setImageResource(getIconCurrentWeather(forecast[2].type))
            imgFourthWeatherType.setImageResource(getIconCurrentWeather(forecast[3].type))
            imgFifthWeatherType.setImageResource(getIconCurrentWeather(forecast[4].type))
            tvFirstWeatherTemp.text = roundTemp(forecast[0].temperature)
            tvSecondWeatherTemp.text = roundTemp(forecast[1].temperature)
            tvThirdWeatherTemp.text = roundTemp(forecast[2].temperature)
            tvFourthWeatherTemp.text = roundTemp(forecast[3].temperature)
            tvFifthWeatherTemp.text = roundTemp(forecast[4].temperature)
        }
    }

    companion object {
        private val THUNDERSTORM = 200..299
        private val DRIZZLE = 300..399
        private val RAIN = 500..599
        private val SNOW = 600..699
        private val ATMOSPHERE = 700..799
        private const val CLEAR = 800
        private val CLOUDS = 801..804
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        private const val KM = 1000
        private val ARR_PERMISSION = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}