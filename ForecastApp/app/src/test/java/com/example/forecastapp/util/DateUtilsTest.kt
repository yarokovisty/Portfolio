package com.example.forecastapp.util

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import java.util.Date

class DateUtilsTest {

    @Test
    fun `test formatUnixTimeHHMM`() {
        val unixTime = 1720766664L

        val expected = "13:44"
        val actual = formatUnixTimeHHMM(unixTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `test formatIsoDate`() {
        val unixTime = 1720766664L

        val expected = "пт, июл. 12"
        val actual = formatIsoDate(unixTime)

        assertEquals(expected, actual)
    }
}