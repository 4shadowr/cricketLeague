package com.example.cricketleague.models.team

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class TeamStats(
        val played: Int,
        val won: Int,
        val lost: Int,
        val points: Int,
) : Parcelable