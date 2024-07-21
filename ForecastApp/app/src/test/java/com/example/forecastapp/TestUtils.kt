package com.example.forecastapp

import com.google.gson.Gson
import java.io.File

object TestUtils {
    private val gson = Gson()

    fun <T> readJsonFromFile(fileName: String, classType: Class<T>): T {
        val file = File(javaClass.classLoader.getResource(fileName).file)
        val json = file.readText()
        return gson.fromJson(json, classType)
    }
}