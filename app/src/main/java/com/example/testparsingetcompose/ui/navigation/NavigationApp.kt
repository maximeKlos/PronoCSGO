package com.example.testparsingetcompose.ui.navigation

import androidx.activity.ComponentActivity
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
fun NavigationApp(context: ComponentActivity) {
    val navController = rememberNavController()
    var startDestination: String
    if(Firebase.auth.currentUser == null) {
        startDestination = AppMainScreens.ConnectionScreen.route
    } else {
        startDestination = AppMainScreens.MainScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination){
        composable(route = AppMainScreens.MainScreen.route){
            BackHandler(true) {}
            MainScreen(context = context, appNavController = navController)
        }
        composable(route = AppMainScreens.ConnectionScreen.route){
            BackHandler(true) {}
            ConnectionScreen(context = context, navController = navController)
        }
        composable(route = AppMainScreens.RegistrationScreen.route){
            BackHandler(true) {}
            RegistrationScreen(context = context, navController = navController)
        }
    }
}