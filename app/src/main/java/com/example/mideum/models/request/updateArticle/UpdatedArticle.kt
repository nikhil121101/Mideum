package com.example.mideum.models.request.updateArticle

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdatedArticle(
    val title: String,
    val body: String,
    val description: String,
)