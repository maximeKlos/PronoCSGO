package com.example.testparsingetcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testparsingetcompose.data.MatchesRepository
import com.example.testparsingetcompose.data.Repository
import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.tools.Result
import kotlinx.coroutines.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _upcomingMatchesList : MutableStateFlow<List<Match>> = MutableStateFlow(emptyList())
    val upcomingMatchesList: StateFlow<List<Match>> = _upcomingMatchesList

    private val _errorState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    fun parsing(eventID : Int) {
        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getMatches(eventID)){
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