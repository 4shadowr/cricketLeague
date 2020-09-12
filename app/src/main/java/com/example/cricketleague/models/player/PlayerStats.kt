package com.example.cricketleague.models.player

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PlayerStats(
        val matchesPlayed: Int,
        val manOfTheMatchWins: Int,
        val bowlerOfTheMatchWins: Int,
        val bestFieldWins: Int,
        val totalPoints: Int,
        val type: String
) : Parcelable