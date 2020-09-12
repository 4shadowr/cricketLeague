package com.example.cricketleague.models.team

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Team(
        val id: String,
        val teamProfile: TeamProfile,
        val teamStats: TeamStats,
        val captainId: String,

        val playersIdList: List<String>
) : Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Team

        if (id != other.id) return false
        if (teamProfile != other.teamProfile) return false
        if (teamStats != other.teamStats) return false
        if (captainId != other.captainId) return false
        if (playersIdList != other.playersIdList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + teamProfile.hashCode()
        result = 31 * result + teamStats.hashCode()
        result = 31 * result + captainId.hashCode()
        result = 31 * result + playersIdList.hashCode()
        return result
    }
}

