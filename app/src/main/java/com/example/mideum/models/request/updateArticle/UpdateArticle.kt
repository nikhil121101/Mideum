package com.example.mideum.models.request.updateArticle

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateArticle(
    val article: UpdatedArticle
)