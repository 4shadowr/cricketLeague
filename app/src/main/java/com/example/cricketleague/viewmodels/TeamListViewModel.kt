package com.example.cricketleague.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricketleague.models.player.Player
import com.example.cricketleague.models.team.Team
import com.example.cricketleague.services.MockService
import kotlinx.coroutines.launch

class TeamListViewModel : ViewModel() {

    private val mockService = MockService.instance
    private val teamPlayers: MutableLiveData<List<Player>> = MutableLiveData()

    val allTeams: MutableLiveData<List<Team>> by lazy {
        MutableLiveData<List<Team>>().also {
            viewModelScope.launch {
                it.value = mockService.allTeams()
            }
        }
    }


    fun getTeamPlayers(teamId: String): LiveData<List<Player>> {
        if (teamPlayers.value == null)
            viewModelScope.launch {
                teamPlayers.value = mockService.allPlayers()!!.filter {
                    it.playerProfile.teamId == teamId
                }
            }
        return teamPlayers
    }

}