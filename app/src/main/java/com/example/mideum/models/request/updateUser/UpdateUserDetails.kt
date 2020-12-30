package com.example.mideum.models.request.updateUser

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserDetails(
    val email: String?,
    val username : String?,
    val password : String?,
    val image: String?,
    val bio: String?
)