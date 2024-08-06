package com.example.randomuser.data.network.dto.userDTO


data class UserDTO(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val userId: UserId,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)