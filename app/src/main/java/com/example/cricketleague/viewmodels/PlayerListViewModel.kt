package com.example.cricketleague.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricketleague.models.player.Player
import com.example.cricketleague.services.MockService
import kotlinx.coroutines.launch

class PlayerListViewModel : ViewModel() {

    private val mockService = MockService.instance

    val sortSelected : MutableLiveData<String> = MutableLiveData()
    val orderSelected : MutableLiveData<Int> = MutableLiveData()
    val filterSelected : MutableLiveData<Int> = MutableLiveData()
    val filterQuery : MutableLiveData<String> = MutableLiveData()
    val isReset:MutableLiveData<Boolean> = MutableLiveData()


    val allPlayers: LiveData<List<Player>> by lazy {
        MutableLiveData<List<Player>>().also {
            viewModelScope.launch {
                it.value = mockService.allPlayers()
            }
        }
    }

}