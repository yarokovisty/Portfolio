package com.example.randomuser.data.mapper

import com.example.randomuser.data.network.dto.userDTO.UserDTO
import com.example.randomuser.domain.entity.UserItem
import com.example.randomuser.util.DateUtils
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserDTOtoUserItem(userDTO: UserDTO): UserItem =
        UserItem(
            id = userDTO.login.uuid,
            title = userDTO.name.title,
            firstname = userDTO.name.first,
            lastname = userDTO.name.last,
            gender = userDTO.gender,
            date = DateUtils.formatToDate(userDTO.dob.date),
            age = userDTO.dob.age,
            phone = userDTO.phone,
            email = userDTO.email,
            lat = userDTO.location.coordinates.latitude.toDouble(),
            lon = userDTO.location.coordinates.longitude.toDouble(),
            country = userDTO.location.country,
            state = userDTO.location.state,
            city = userDTO.location.city,
            nat = userDTO.nat
        )
}