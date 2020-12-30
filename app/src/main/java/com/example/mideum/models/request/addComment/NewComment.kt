package com.example.mideum.models.request.addComment

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewComment(
    val body: String
)