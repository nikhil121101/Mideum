package com.example.mideum.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val author: Profile,
    val body: String,
    val createdAt: String,
    val id: Int,
    val updatedAt: String
)