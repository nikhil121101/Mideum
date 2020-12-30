package com.example.mideum.models.response

import com.example.mideum.models.domain.Profile
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseProfile(
    val profile: Profile
)