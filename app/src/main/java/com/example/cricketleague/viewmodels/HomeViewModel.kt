package com.example.cricketleague.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricketleague.models.team.Team
import com.example.cricketleague.services.MockService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val mockService: MockService = MockService.instance

    private val standings: MutableLiveData<List<Team>> = MutableLiveData()

    fun getStandings(): LiveData<List<Team>> {
        if (standings.value == null) {
            viewModelScope.launch {
                standings.value = mockService.allTeams()!!.sortedByDescending {
                    it.teamStats.points
                }.subList(0,5)
            }
        }
        return standings
    }
}