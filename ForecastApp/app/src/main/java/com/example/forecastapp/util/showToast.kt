package com.example.forecastapp.util

import android.content.Context
import android.widget.Toast
import com.example.forecastapp.R

fun showShortToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}