package com.example.testparsingetcompose.data.model

data class Match(
    val matchId : String,
    val format : String,
    val pageLink : String,
    val timeUnix : String,
    val team1LogoURL : String,
    val team1Name : String,
    val team1OddVictory : Int = -1,
    val team1Score : Int = -1,
    val team2LogoURL : String,
    val team2Name : String,
    val team2OddVictory : Int = -1,
    val team2Score : Int = -1
)