package com.example.testparsingetcompose.ui.screens.information

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import testparsingetcompose.BuildConfig
import testparsingetcompose.R


@Composable
fun InformationScreen() {
    val mContext = LocalContext.current
    val twitterIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/maximeklos"))
    val steamIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.steamcommunity.com/id/maxlelorrain"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo app")
        Text(text = "Prono CS:GO")
        Text(text = "Version ${BuildConfig.VERSION_NAME}")
        Text(text = "© 2022 - Maxime KLOS")

        Row(
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.steam),
                contentDescription = "steam logo",
                modifier = Modifier
                    .size(64.dp)
                    .width(64.dp)
                    .height(64.dp)
                    .clickable {
                        mContext.startActivity(steamIntent)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "logo twitter",
                modifier = Modifier
                    .padding(start = 32.dp)
                    .width(64.dp)
                    .height(64.dp)
                    .clickable {
                        mContext.startActivity(twitterIntent)
                    }
            )
        }
    }
}