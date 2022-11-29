package com.example.testparsingetcompose.ui.screens.upcomingmatches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testparsingetcompose.data.Repository
import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.model.MatchScore
import com.example.testparsingetcompose.data.tools.Result
import kotlinx.coroutines.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _upcomingMatchesList : MutableStateFlow<List<Match>> = MutableStateFlow(emptyList())
    val upcomingMatchesList: StateFlow<List<Match>> = _upcomingMatchesList

    private val _matchesScoreList : MutableStateFlow<List<MatchScore>> = MutableStateFlow(emptyList())
    val matchesScoreList : StateFlow<List<MatchScore>> = _matchesScoreList

    private val _errorState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    fun parsing(eventID : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getUpcomingMatchesFromHLTV(eventID)){
                is Result.Success -> {
                    _upcomingMatchesList.emit(response.data)
                }
                is Result.Error -> {
                    _errorState.emit(true)
                }
            }
        }
    }
}