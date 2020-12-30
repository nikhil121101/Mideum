package com.example.mideum.models.request.signUp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpUser(
    val user : SignUpUserDetails
)