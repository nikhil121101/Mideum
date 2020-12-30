package com.example.mideum.models.request.signIn

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInUser(
    val user: SignInUserDetails
)