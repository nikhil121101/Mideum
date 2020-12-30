package com.example.mideum.models.response

import com.example.mideum.models.domain.User
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseUser(
    val user: User
)