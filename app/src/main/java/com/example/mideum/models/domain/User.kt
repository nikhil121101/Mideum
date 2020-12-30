package com.example.mideum.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val bio: String?,
    val email: String,
    val image: String?,
    val token: String,
    val username: String
)

fun User.toProfile() : Profile {
    return Profile(bio , false , image , username)
}