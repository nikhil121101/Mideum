package com.example.mideum.models.request.createArticle

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewArticle(
    val body: String,
    val description: String,
    val tagList: List<String>?,
    val title: String
)