package com.example.mideum.models.request.createArticle

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateArticle(
    val article: NewArticle
)