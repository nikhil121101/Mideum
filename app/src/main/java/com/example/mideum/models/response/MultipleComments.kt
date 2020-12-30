package com.example.mideum.models.response

import com.example.mideum.models.domain.Comment
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultipleComments(
    val comments: List<Comment>
)