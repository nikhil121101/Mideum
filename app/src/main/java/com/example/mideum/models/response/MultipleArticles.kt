package com.example.mideum.models.response

import com.example.mideum.models.domain.Article
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultipleArticles(
    val articles: List<Article>,
    val articlesCount: Int
)