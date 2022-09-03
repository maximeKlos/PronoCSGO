package com.example.testparsingetcompose.data

import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.tools.Result

class MatchesRepository(
    private val remoteDataSource: MatchesDataSource
): Repository {
    override suspend fun getMatches(eventID: Int): Result<List<Match>> = remoteDataSource.getMatches(eventID)
}