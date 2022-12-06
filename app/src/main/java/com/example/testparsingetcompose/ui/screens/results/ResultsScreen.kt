package com.example.testparsingetcompose.ui.screens.results

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.ui.components.TeamInfoDisplay
import testparsingetcompose.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Composable
fun ResultsScreen(
    resultsViewModel: ResultsViewModel = hiltViewModel()
) {

    val resultsList = resultsViewModel.resultsList.collectAsState().value
    val errorState = resultsViewModel.errorState.collectAsState().value

    if (resultsList.isEmpty()) {
        LaunchedEffect(resultsList) {
            resultsViewModel.parsing(6714)
        }
    }

    if (errorState) {
        Text(text = R.string.error_fetching_data.toString())
    }

    LazyColumn {
        itemsIndexed(resultsList) { _, match ->
            ResultCell(
                match = match
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ResultCell(match: Match) {

    val timestamp = Timestamp(match.timeUnix.toLong())
    val date = Date(timestamp.time)
    val dateFormat = SimpleDateFormat("dd/MM HH:mm")
    val matchTime = dateFormat.format(date)
    val context = LocalContext.current
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
                        .padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = match.team1Score.toString(),
                        fontSize = 32.sp,
                        modifier = Modifier.wrapContentSize()
                    )
                    Text(
                        text = " - ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = match.team2Score.toString(),
                        fontSize = 32.sp,
                        modifier = Modifier.wrapContentSize()
                    )
                }
                Text(
                    text = "Best of ${match.format.substring(2)}",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 8.dp)
                )
            }

            TeamInfoDisplay(
                teamName = match.team2Name,
                teamLogoURL = match.team2LogoURL
            )
        }
    }
}
