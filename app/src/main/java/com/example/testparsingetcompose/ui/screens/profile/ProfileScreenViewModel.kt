package com.example.testparsingetcompose.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testparsingetcompose.data.Repository
import com.example.testparsingetcompose.data.tools.Result
import com.example.testparsingetcompose.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _errorState : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> = _errorState

    private var _user : MutableStateFlow<User?> = MutableStateFlow(null)
    var user: StateFlow<User?> = _user

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getUser()){
                is Result.Success -> {
                    _user.emit(response.data)
                }
                is Result.Error -> {
                    _errorState.emit(true)
                }
            }
        }
    }
}