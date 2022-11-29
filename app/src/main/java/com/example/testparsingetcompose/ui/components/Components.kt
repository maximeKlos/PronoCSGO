package com.example.testparsingetcompose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun TeamInfoDisplay(teamName: String, teamLogoURL: String) {

    val url = if (teamLogoURL == "/img/static/team/placeholder.svg") {
        "https://www.hltv.org/img/static/team/placeholder.svg"
    } else {
        teamLogoURL
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = teamName,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.widthIn(0.dp, 96.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = "$teamName logo",
            modifier = Modifier
                .height(96.dp)
                .padding(top = 4.dp)
        )
    }
}