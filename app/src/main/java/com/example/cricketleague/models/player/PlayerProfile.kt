package com.example.cricketleague.models.player

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PlayerProfile(
        val firstName: String,
        val lastName: String,
        val profileImgUrl: String,
        val age: Int,
        val country: String,
        val teamId: String
) : Parcelable {

    val fullName: String
        get() = "$firstName $lastName"

}