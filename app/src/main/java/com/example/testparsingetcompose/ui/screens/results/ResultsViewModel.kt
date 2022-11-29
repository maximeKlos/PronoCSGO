package com.example.testparsingetcompose.ui.screens.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testparsingetcompose.data.Repository
import com.example.testparsingetcompose.data.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.testparsingetcompose.data.tools.Result
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _resultsList : MutableStateFlow<List<Match>> = MutableStateFlow(emptyList())
    val resultsList: StateFlow<List<Match>> = _resultsList

    private val _errorState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    fun parsing(eventID: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getResults(eventID)){
                is Result.Success -> {
                    _resultsList.emit(response.data)
                }
                is Result.Error -> {
                    _errorState.emit(true)
                }
            }
        }
    }
}