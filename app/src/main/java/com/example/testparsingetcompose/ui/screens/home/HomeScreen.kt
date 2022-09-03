package com.example.testparsingetcompose.ui.screens.home

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testparsingetcompose.data.model.Match

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val upcomingMatchesList = homeViewModel.upcomingMatchesList.collectAsState().value
    val errorState = homeViewModel.errorState.collectAsState().value

    LaunchedEffect(upcomingMatchesList){
        homeViewModel.parsing(6714)
    }

    if(errorState){
        Text(text = "Un problème est survenu lors de la récupération des données")
    }

    LazyColumn {
        itemsIndexed(upcomingMatchesList) { index, match ->
            MatchCell(
                match = match,
                index = index
            )
        }
    }
}

@Composable
fun MatchCell(match: Match, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp)),
        elevation = 10.dp
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color(0xFFCDDFFF))
                .animateContentSize()
                .padding(4.dp)
        ) {
            Text(
                text = match.team1Name,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 8.dp )
            )
            Text(
                text = match.team2Name,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
