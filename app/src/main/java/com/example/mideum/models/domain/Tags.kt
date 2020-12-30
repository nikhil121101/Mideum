package com.example.mideum.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tags(
    val tags: List<String>
)