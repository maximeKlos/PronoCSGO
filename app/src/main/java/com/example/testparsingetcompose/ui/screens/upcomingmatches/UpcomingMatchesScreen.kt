package com.example.testparsingetcompose.ui.screens.upcomingmatches

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import testparsingetcompose.R
import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.model.MatchScore
import com.example.testparsingetcompose.ui.TeamInfoDisplay
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Composable
fun UpcomingMatchesScreen(
    upcomingMatchesViewModel: UpcomingMatchesViewModel = hiltViewModel()
) {

    val upcomingMatchesList = upcomingMatchesViewModel.upcomingMatchesList.collectAsState().value
    val errorState = upcomingMatchesViewModel.errorState.collectAsState().value
    val matchesScoreList = upcomingMatchesViewModel.matchesScoreList.collectAsState().value

    if (upcomingMatchesList.isEmpty()) {
        LaunchedEffect(upcomingMatchesList) {
            upcomingMatchesViewModel.parsing(6588)
        }
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO -- validate pronostics*/},
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
                ) {
                Icon(Icons.Filled.Check, "")
            }
        }
    ) {
        if (errorState) {
            Text(text = R.string.error_fetching_data.toString())
        }

        LazyColumn {
            itemsIndexed(upcomingMatchesList) { _, match ->
                MatchCell(
                    match = match,
                    matchesScoreList = matchesScoreList
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun MatchCell(match: Match, matchesScoreList: List<MatchScore>) {

    var scoreTeam1 by remember { mutableStateOf("") }
    var scoreTeam2 by remember { mutableStateOf("") }
    val timestamp = Timestamp(match.timeUnix.toLong())
    val date = Date(timestamp.time)
    val dateFormat = SimpleDateFormat("dd/MM - HH:mm")
    val matchTime = dateFormat.format(date)
    val context = LocalContext.current
    val scoreMax = when (match.format) {
        "bo1" -> 16
        "bo3" -> 2
        "bo5" -> 3
        else -> -1
    }
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(match.pageLink))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { context.startActivity(webIntent) },
        elevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(color = Color(0xFFFF9800))
                .animateContentSize()
                .padding(4.dp)
        ) {
            TeamInfoDisplay(
                teamName = match.team1Name,
                teamLogoURL = match.team1LogoURL
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = matchTime,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = "best of ${match.format.substring(2)}",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        OutlinedTextField(
                            value = scoreTeam1,
                            onValueChange = {
                                scoreTeam1 = it
                                if (scoreTeam1.isNotEmpty()) {
                                    if (it.matches(Regex("[0-9]*"))) {
                                        if (scoreTeam1.toInt() >= scoreMax) {
                                            if (scoreTeam2.isEmpty()) scoreTeam1 = "$scoreMax"
                                            else if (scoreTeam2.toInt() < scoreMax - 1) scoreTeam1 =
                                                "$scoreMax"
                                            else {
                                                scoreTeam2 = "${scoreMax - 1}"
                                                scoreTeam1 = if (scoreMax == 16) {
                                                    "${scoreMax - 1}"
                                                } else {
                                                    "$scoreMax"
                                                }
                                            }
                                        } else {
                                            scoreTeam1 = it
                                        }
                                    } else {
                                        scoreTeam1 = ""
                                        Toast.makeText(
                                            context,
                                            R.string.error_not_number,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            modifier = Modifier.width(50.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(
                            text = match.team1OddVictory.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = MaterialTheme.colors.primary)
                                .width(50.dp)
                                .padding(2.dp)
                        )
                    }
                    Text(
                        text = " - ",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        OutlinedTextField(
                            value = scoreTeam2,
                            onValueChange = {
                                scoreTeam2 = it
                                if (scoreTeam2.isNotEmpty()) {
                                    if (it.matches(Regex("[0-9]*"))) {
                                        if (scoreTeam2.toInt() >= scoreMax) {
                                            if (scoreTeam1.isEmpty()) scoreTeam2 = "$scoreMax"
                                            else if (scoreTeam1.toInt() < scoreMax - 1) scoreTeam2 =
                                                "$scoreMax"
                                            else {
                                                scoreTeam1 = "${scoreMax - 1}"
                                                scoreTeam2 = if (scoreMax == 16) {
                                                    "${scoreMax - 1}"
                                                } else {
                                                    "$scoreMax"
                                                }
                                            }
                                        } else {
                                            scoreTeam2 = it
                                        }
                                    } else {
                                        scoreTeam2 = ""
                                        Toast.makeText(
                                            context,
                                            R.string.error_not_number,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            modifier = Modifier.width(50.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(
                            text = match.team2OddVictory.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = MaterialTheme.colors.primary)
                                .width(50.dp)
                                .padding(2.dp)
                        )
                    }
                }
            }
            TeamInfoDisplay(
                teamName = match.team2Name,
                teamLogoURL = match.team2LogoURL
            )
        }
    }
}
