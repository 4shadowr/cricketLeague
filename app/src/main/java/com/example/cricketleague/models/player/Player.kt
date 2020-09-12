package com.example.cricketleague.models.player

import android.os.Parcelable
import com.example.cricketleague.Utils.FilterUtils.COMP
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*
import java.util.function.Predicate

@JsonClass(generateAdapter = true)
@Parcelize
data class Player(
        val id: String,
        val playerProfile: PlayerProfile,
        val playerStats: PlayerStats
) : Parcelable {


    enum class PlayerComparator : Comparator<Player> {
        FIRST_NAME_SORT {
            override fun compare(o1: Player, o2: Player): Int {
                return o1.playerProfile.firstName.compareTo(o2.playerProfile.firstName)
            }
        },
        LAST_NAME_SORT {
            override fun compare(o1: Player, o2: Player): Int {
                return o1.playerProfile.lastName.compareTo(o2.playerProfile.lastName)
            }
        },
        POINTS_SORT {
            override fun compare(o1: Player, o2: Player): Int {
                return o1.playerStats.totalPoints - o2.playerStats.totalPoints
            }
        },
        MATCHES_PLAYED_SORT {
            override fun compare(o1: Player, o2: Player): Int {
                return o1.playerStats.matchesPlayed - o2.playerStats.matchesPlayed
            }
        };

        companion object {
            fun descending(other: Comparator<Player?>): Comparator<Player> {
                return Comparator { o1: Player?, o2: Player? -> -1 * other.compare(o1, o2) }
            }
        }
    }

    companion object {


        @JvmStatic
        fun ofCountry(country: String): Predicate<Player> {
            return Predicate { player: Player -> player.playerProfile.country.toLowerCase(Locale.ROOT) == country.toLowerCase(Locale.ROOT).trim { it <= ' ' } }
        }

        @JvmStatic
        fun ofType(type: String): Predicate<Player> {
            return Predicate { player: Player -> player.playerStats.type.toLowerCase(Locale.ROOT) == type.toLowerCase(Locale.ROOT).trim { it <= ' ' } }
        }

        fun ofTeam(teamId: String): Predicate<Player> {
            return Predicate { player: Player -> player.playerProfile.country.toLowerCase(Locale.ROOT) == teamId.toLowerCase(Locale.ROOT).trim { it <= ' ' } }
        }

        fun whereMatchesPlayed(matchesPlayed: Int, COMPARISON: COMP): Predicate<Player> {
            return when (COMPARISON) {
                COMP.LESS -> Predicate { player: Player -> player.playerStats.matchesPlayed < matchesPlayed }
                COMP.GREATER -> Predicate { player: Player -> player.playerStats.matchesPlayed > matchesPlayed }
                COMP.EQUALS -> Predicate { player: Player -> player.playerStats.matchesPlayed == matchesPlayed }
                else -> Predicate { player: Player -> player.playerStats.matchesPlayed == matchesPlayed }
            }
        }

        fun whereAge(age: Int, COMPARISON: COMP): Predicate<Player> {
            return when (COMPARISON) {
                COMP.LESS -> Predicate { player: Player -> player.playerProfile.age < age }
                COMP.GREATER -> Predicate { player: Player -> player.playerProfile.age > age }
                COMP.EQUALS -> Predicate { player: Player -> player.playerProfile.age == age }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (playerProfile != other.playerProfile) return false
        if (playerStats != other.playerStats) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + playerProfile.hashCode()
        result = 31 * result + playerStats.hashCode()
        return result
    }
}