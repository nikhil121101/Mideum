package com.example.mideum.models.request.updateUser

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUser(
    val user: UpdateUserDetails
)