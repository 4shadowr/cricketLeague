package com.example.cricketleague.models.team

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class TeamProfile(
         val name: String,
         val teamCode: String,
         val imageUrl: String?
) : Parcelable