package com.example.mideum.models.domain

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Profile(
    val bio: String?,
    val following: Boolean,
    val image: String?,
    val username: String
) : Parcelable