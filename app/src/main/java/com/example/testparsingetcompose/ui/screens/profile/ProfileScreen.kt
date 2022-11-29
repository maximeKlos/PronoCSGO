package com.example.testparsingetcompose.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.testparsingetcompose.ui.navigation.AppMainScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import testparsingetcompose.R

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val user = profileScreenViewModel.user.collectAsState().value
    val errorState = profileScreenViewModel.errorState.collectAsState().value

    if (user == null) {
        LaunchedEffect(user) {
            profileScreenViewModel.getUser()
        }
    }

    if (errorState) {
        Text(text = R.string.error_fetching_data.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            Firebase.auth.signOut()
            navController.navigate(AppMainScreens.ConnectionScreen.route)
        }) {
            Text(text = "DISCONNECT", color = Color.White)
        }
        if (user?.isAdmin == true) {
            Button(onClick = {

            }) {
                Text(text = "GET UPCOMING MATCHES", color = Color.White)
            }
        }
    }
}