package com.example.testparsingetcompose.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testparsingetcompose.ui.screens.authentication.ConnectionScreen
import com.example.testparsingetcompose.ui.screens.information.InformationScreen
import com.example.testparsingetcompose.ui.screens.results.ResultsScreen
import com.example.testparsingetcompose.ui.screens.profile.ProfileScreen
import com.example.testparsingetcompose.ui.screens.upcomingmatches.UpcomingMatchesScreen

@Composable
fun NavigationScreen(
    navController: NavHostController,
    context: ComponentActivity,
    appNavController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = NavigationBarScreens.UpcomingMatches.route
    ) {
        composable(route = NavigationBarScreens.Results.route){
            BackHandler(true) {}
            ResultsScreen()
        }
        composable(route = NavigationBarScreens.UpcomingMatches.route){
            BackHandler(true) {}
            UpcomingMatchesScreen()
        }
        composable(route = NavigationBarScreens.Ranking.route){
            BackHandler(true) {}
            //TODO -- Create Ranking AppMainScreens
        }
        composable(route = NavigationBarScreens.Profile.route){
            BackHandler(true) {}
            ProfileScreen(navController = appNavController)
        }
        composable(route = NavigationBarScreens.Informations.route){
            BackHandler(true) {}
            InformationScreen()
        }
        composable(route = AppMainScreens.ConnectionScreen.route){
            BackHandler(true) {}
            ConnectionScreen(context = context, navController = navController)
        }
    }
}