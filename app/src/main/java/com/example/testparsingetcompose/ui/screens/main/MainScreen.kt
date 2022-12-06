package com.example.testparsingetcompose.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testparsingetcompose.ui.navigation.NavigationScreen
import com.example.testparsingetcompose.ui.navigation.NavigationBarScreens

@Composable
fun MainScreen(appNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationScreen(navController = navController, appNavController = appNavController)
        }
    }
}

@Composable
fun TopBar(navController: NavHostController) {
    val screens = listOf(
        NavigationBarScreens.Profile,
        NavigationBarScreens.Informations
    )

    TopAppBar(
        title = {
            Text(text = "Pronos CS:GO")
        },
        actions = {
            screens.forEach { screen ->
                IconButton(onClick = { navController.navigate(screen.route) }) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationBarScreens.UpcomingMatches,
        NavigationBarScreens.Results,
        NavigationBarScreens.Ranking
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}