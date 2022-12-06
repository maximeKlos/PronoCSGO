package com.example.testparsingetcompose.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testparsingetcompose.ui.screens.authentication.ConnectionScreen
import com.example.testparsingetcompose.ui.screens.authentication.RegistrationScreen
import com.example.testparsingetcompose.ui.screens.main.MainScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val startDestination =
        if (Firebase.auth.currentUser == null) {
            AppMainScreens.ConnectionScreen.route
        } else {
            AppMainScreens.MainScreen.route
        }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = AppMainScreens.MainScreen.route) {
            BackHandler(true) {}
            MainScreen(appNavController = navController)
        }
        composable(route = AppMainScreens.ConnectionScreen.route) {
            BackHandler(true) {}
            ConnectionScreen(navController = navController)
        }
        composable(route = AppMainScreens.RegistrationScreen.route) {
            BackHandler(true) {}
            RegistrationScreen(navController = navController)
        }
    }
}