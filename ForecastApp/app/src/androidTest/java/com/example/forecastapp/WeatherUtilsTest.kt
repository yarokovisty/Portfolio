package com.example.forecastapp

import com.example.forecastapp.util.WeatherUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class WeatherUtilsTest {

    @Test
    fun testGetBackgroundColor() {
        // Arrange
        val weatherUtils = WeatherUtils

        // Test cases
        val testCases = listOf(
            Pair(201, Pair(R.color.primary_bg_thunderstorm, R.color.secondary_bg_thunderstorm)),
            Pair(500, Pair(R.color.primary_bg_rainy, R.color.secondary_bg_rainy)),
            Pair(611, Pair(R.color.primary_bg_snow, R.color.secondary_bg_snow)),
            Pair(741, Pair(R.color.primary_bg_mist, R.color.secondary_bg_mist)),
            Pair(800, Pair(R.color.primary_bg_clear, R.color.secondary_bg_clear)),
            Pair(802, Pair(R.color.primary_bg_cloudy, R.color.secondary_bg_cloudy))
        )

        // Act & Assert
        for ((id, expectedPair) in testCases) {
            val (primaryColorRes, secondaryColorRes) = weatherUtils.getBackgroundColor(id)
            assertEquals(expectedPair.first, primaryColorRes)
            assertEquals(expectedPair.second, secondaryColorRes)
        }
    }

    @Test
    fun testGetBackgroundColorError() {
        // Arrange
        val weatherUtils = WeatherUtils

        // Test case
        val invalidId = 999
        val exception = assertThrows(IllegalArgumentException::class.java) {
            weatherUtils.getBackgroundColor(invalidId)
        }

        // Assert
        assertEquals("Invalid weather id: $invalidId", exception.message)
    }
}