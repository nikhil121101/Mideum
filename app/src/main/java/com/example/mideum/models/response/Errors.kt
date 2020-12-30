package com.example.mideum.models.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Errors(
    val body: List<String>
)