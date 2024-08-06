package com.example.randomuser.domain.entity

data class UserItem(
    val id: String,
    val title: String,
    val firstname: String,
    val lastname: String,
    val gender: String,
    val date: String,
    val age: Int,
    val phone: String,
    val email: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String,
    val city: String,
    val nat: String
)
