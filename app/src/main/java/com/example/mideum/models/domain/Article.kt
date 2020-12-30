package com.example.mideum.models.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Article(
    val author: @RawValue Profile,
    val body: String,
    val createdAt: String,
    val description: String,
    val favorited: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tagList: List<String>?,
    val title: String,
    val updatedAt: String
) : Parcelable