package com.example.mideum.models.request.signIn

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInUserDetails(
    val email: String,
    val password: String
)