package com.example.mideum.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseErrors(
    val errors: Errors
)