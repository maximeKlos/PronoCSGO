package com.example.testparsingetcompose.data.model

data class Match(
    val format : String,
    val analyticsLink : String,
    val timeUnix : String,
    val team1LogoURL : String,
    val team1Name : String,
    val team1OddVictory : Int,
    val team2LogoURL : String,
    val team2Name : String,
    val team2OddVictory : Int,
)