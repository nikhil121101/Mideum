package com.example.mideum.models.request.addComment

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddComment(
    val comment: NewComment
)