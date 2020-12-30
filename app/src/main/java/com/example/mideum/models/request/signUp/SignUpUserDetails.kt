package com.example.mideum.models.request.signUp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpUserDetails(
    val email: String,
    val password: String,
    val username: String
)