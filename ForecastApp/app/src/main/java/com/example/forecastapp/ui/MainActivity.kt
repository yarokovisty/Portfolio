package com.example.forecastapp.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.R
import com.example.forecastapp.databinding.ActivityMainBinding
import com.example.forecastapp.domain.entity.CurrentWeatherItem
import com.example.forecastapp.domain.entity.DailyForecastItem
import com.example.forecastapp.domain.entity.HourlyForecastItem
import com.example.forecastapp.presentation.ForecastApplication
import com.example.forecastapp.presentation.state.ForecastState
import com.example.forecastapp.presentation.viewmodel.ForecastViewModel
import com.example.forecastapp.presentation.viewmodel.ViewModelFactory
import com.example.forecastapp.ui.adapter.DailyForecastAdapter
import com.example.forecastapp.util.WeatherUtils
import com.example.forecastapp.util.showShortToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DailyForecastAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient


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
        setRecyclerView()
        setSplashScreen()
        setLocation()
    }

    override fun onStart() {
        super.onStart()

        loadData()
    }

    private fun initComponent() {
        val component = (applicationContext as ForecastApplication).appComponent
        component.inject(this)
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.Main).launch {
            val isOnline = withContext(Dispatchers.IO) { isOnline(this@MainActivity) }
            if (isOnline) {
                getCurrentLocation()
            } else {
                viewModel.loadDataFromDb()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.forecastState.observe(this, ::renderState)
    }

    private fun renderState(state: ForecastState) {

        when (state) {
            ForecastState.Initial -> {}
            ForecastState.Loading -> renderLoading()
            is ForecastState.Success -> renderSuccess(
                state.currentWeatherItem,
                state.listHourlyForecastItem,
                state.listDailyForecastItem
            )

            is ForecastState.Error -> renderError(state.errorMessage)
        }
    }

    private fun renderLoading() {

    }

    private fun renderSuccess(
        currentWeather: CurrentWeatherItem,
        listHourlyForecastItem: List<HourlyForecastItem>,
        listDailyForecastItem: List<DailyForecastItem>
    ) {

        renderCurrentWeather(currentWeather)
        renderHourlyForecast(listHourlyForecastItem)
        renderDailyForecast(listDailyForecastItem)
    }

    private fun renderError(error: String) {
        showShortToast(this, error)
    }

    private fun renderCurrentWeather(currentWeather: CurrentWeatherItem) = with(binding) {
        tvTempCurrentWeather.text =
            getString(R.string.template_temperature, currentWeather.temp.toString())
        tvDescriptionCurrentWeather.text = currentWeather.description
        tvWindValue.text = getString(R.string.template_wind, currentWeather.wind.toString())
        tvHumidityValue.text =
            getString(R.string.template_humidity, currentWeather.humidity.toString())
        tvVisibilityValue.text = getString(
            R.string.template_visibility, currentWeather.visibility.toString()
        )

        val (primaryBackgroundColor, secondaryBackgroundColor) =
            WeatherUtils.getBackgroundColor(currentWeather.id)

        setPrimaryBackgroundColor(primaryBackgroundColor)
        setSecondaryBackgroundColor(secondaryBackgroundColor)
        setIconCurrentWeather(currentWeather.id)
    }

    private fun renderHourlyForecast(listHourlyForecastItem: List<HourlyForecastItem>) =
        with(binding) {
            tvTimeHourlyForecast1.text = listHourlyForecastItem[0].time
            tvTimeHourlyForecast2.text = listHourlyForecastItem[1].time
            tvTimeHourlyForecast3.text = listHourlyForecastItem[2].time
            tvTimeHourlyForecast4.text = listHourlyForecastItem[3].time
            tvTimeHourlyForecast5.text = listHourlyForecastItem[4].time

            imgTypeWeatherHourlyForecast1.setImageResource(
                WeatherUtils.getIconWeather(
                    listHourlyForecastItem[0].id
                )
            )
            imgTypeWeatherHourlyForecast2.setImageResource(
                WeatherUtils.getIconWeather(
                    listHourlyForecastItem[1].id
                )
            )
            imgTypeWeatherHourlyForecast3.setImageResource(
                WeatherUtils.getIconWeather(
                    listHourlyForecastItem[2].id
                )
            )
            imgTypeWeatherHourlyForecast4.setImageResource(
                WeatherUtils.getIconWeather(
                    listHourlyForecastItem[3].id
                )
            )
            imgTypeWeatherHourlyForecast5.setImageResource(
                WeatherUtils.getIconWeather(
                    listHourlyForecastItem[4].id
                )
            )

            tvTempHourlyForecast1.text = getString(
                R.string.template_temperature,
                listHourlyForecastItem[0].temp.toString()
            )
            tvTempHourlyForecast2.text = getString(
                R.string.template_temperature,
                listHourlyForecastItem[1].temp.toString()
            )
            tvTempHourlyForecast3.text = getString(
                R.string.template_temperature,
                listHourlyForecastItem[2].temp.toString()
            )
            tvTempHourlyForecast4.text = getString(
                R.string.template_temperature,
                listHourlyForecastItem[3].temp.toString()
            )
            tvTempHourlyForecast5.text = getString(
                R.string.template_temperature,
                listHourlyForecastItem[4].temp.toString()
            )
        }

    private fun renderDailyForecast(listDailyForecastItem: List<DailyForecastItem>) {
        adapter.listDailyForecast = listDailyForecastItem
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
        binding.cardViewHourlyForecast.setCardBackgroundColor(
            ContextCompat.getColor(this, secondaryBackgroundColor)
        )
        binding.cardViewDailyForecast.setCardBackgroundColor(
            ContextCompat.getColor(this, secondaryBackgroundColor)
        )
    }

    private fun setIconCurrentWeather(idCurrentWeather: Int) {
        binding.imgCurrentWeather.setImageResource(WeatherUtils.getIconWeather(idCurrentWeather))
    }

    private fun setRecyclerView() {
        adapter = DailyForecastAdapter()
        binding.rvDailyForecast.adapter = adapter
    }

    private fun setSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isReady) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }

            }
        )
    }

    private suspend fun isOnline(context: Context): Boolean {
        return if (isNetworkAvailable(context)) {
            isInternetAvailable()
        } else {
            false
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(GOOGLE_URL)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 3000
                urlConnection.connect()
                val responseCode = urlConnection.responseCode
                urlConnection.disconnect()
                responseCode == 200
            } catch (e: IOException) {
                false
            }
        }
    }


    private fun setLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {

            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result

                    if (location == null) {
                        showShortToast(this, getString(R.string.error_location_null))
                    } else {
                        viewModel.loadDataFromNetwork(location.longitude, location.latitude)
                    }
                }
            } else {
                intentLocation()
            }
        } else {
            requestPermission()
        }

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun checkPermission(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_ACCESS_LOCATION)
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
            }
        } else {
            showShortToast(this, getString(R.string.error_reject_permission))
        }
    }

    private fun intentLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    companion object {
        private const val GOOGLE_URL = "https://www.google.com"
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 101
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}