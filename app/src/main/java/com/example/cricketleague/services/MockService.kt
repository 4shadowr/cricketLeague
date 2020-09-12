package com.example.cricketleague.services

import android.app.Application
import com.example.cricketleague.models.Country
import com.example.cricketleague.models.team.Team
import com.example.cricketleague.models.player.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class MockService : Application() {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    suspend fun getAllCountries(): List<Country>? =
            withContext(Dispatchers.IO) {
                val type: Type = Types.newParameterizedType(MutableList::class.java, Country::class.java)
                val adapter = moshi.adapter<List<Country>>(type)

                val json: String = getJsonFromFile(COUNTRIES_JSON)

                adapter.fromJson(json)
            }


    suspend fun allTeams(): List<Team>? =
            withContext(Dispatchers.IO) {
                val type: Type = Types.newParameterizedType(MutableList::class.java, Team::class.java)
                val adapter = moshi.adapter<List<Team>>(type)

                val json: String = getJsonFromFile(TEAMS_JSON)

                adapter.fromJson(json)
            }

    private fun getJsonFromFile(fileName: String): String {
        return assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }


    suspend fun allPlayers(): List<Player>? =
            withContext(Dispatchers.IO) {
                val type: Type = Types.newParameterizedType(MutableList::class.java, Player::class.java)
                val adapter = moshi.adapter<List<Player>>(type)

                val json: String = getJsonFromFile(PLAYERS_JSON)

                adapter.fromJson(json)
            }

//    fun test() {
//        val teamsList: MutableList<Team> = ArrayList()
//        for (i in 1..12) {
//            val id = "t_$i"
//            val captainId = i.toString() + "0"
//            val imageUrl = ""
//            val teamProfile = TeamProfile("namyy_$i", "NM$i", imageUrl)
//            val teamStats = TeamStats(20, 10, 10, 20)
//            val idList: MutableList<String> = ArrayList()
//            for (j in 1..14) idList.add(i.toString() + "" + j)
//            val current = Team(id, teamProfile, teamStats, captainId, idList)
//            teamsList.add(current)
//        }
//        val type: Type = Types.newParameterizedType(MutableList::class.java, Team::class.java)
//        val adapter = moshi.adapter<List<Team>>(type)
//        val json = adapter.toJson(teamsList)
//        Log.d(TAG, "JSON TEST: START ================================================================")
//        Log.d(TAG, "JSON TEAM: $json")
//        Log.d(TAG, "JSON TEST: END ===================================================================")
//    }

    companion object {
        private const val TAG = "MockService"

        lateinit var instance: MockService
        const val COUNTRIES_JSON = "countries.json"
        const val PLAYERS_JSON = "players.json"
        const val TEAMS_JSON = "teams.json"
    }

}