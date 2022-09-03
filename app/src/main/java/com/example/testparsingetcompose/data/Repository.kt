package com.example.testparsingetcompose.data

import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.tools.Result

interface Repository {

    suspend fun getMatches(eventID: Int): Result<List<Match>>

}