package com.example.testparsingetcompose.data

import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.model.User
import com.example.testparsingetcompose.data.tools.Result

class MyRepository(
    private val remoteDataSource: MyDataSource
): Repository {
    override suspend fun getUpcomingMatchesFromHLTV(eventID: Int): Result<List<Match>> = remoteDataSource.getUpcomingMatchesFromHLTV(eventID)
    override suspend fun getUpcomingMatchesFromFirestore(eventID: Int): Result<List<Match>> = remoteDataSource.getUpcomingMatchesFromFirestore(eventID)
    override suspend fun getResults(eventID: Int): Result<List<Match>> = remoteDataSource.getResults(eventID)
    override suspend fun getUser(): Result<User?> = remoteDataSource.getUser()
}