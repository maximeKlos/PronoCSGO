package com.example.testparsingetcompose.data

import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.model.MatchScore
import com.example.testparsingetcompose.data.model.User
import com.example.testparsingetcompose.data.tools.Result

interface MyDataSource {

    suspend fun getUpcomingMatchesFromHLTV(eventID: Int): Result<List<Match>>
    suspend fun getResults(eventID: Int): Result<List<Match>>
    suspend fun getUpcomingMatchesFromFirestore(eventID: Int): Result<List<Match>>
    suspend fun getUser(): Result<User?>
}