package com.example.testparsingetcompose.ui.navigation

import testparsingetcompose.R


sealed class NavigationBarScreens(
    val route : String,
    val title : String,
    val icon : Int
) {
    object UpcomingMatches : NavigationBarScreens(
        route = "upcomingmatches",
        title = "Matches",
        icon = R.drawable.icons_upcoming_matches
    )

    object Results : NavigationBarScreens(
        route = "results",
        title = "Results",
        icon = R.drawable.icons_results
    )

    object Ranking : NavigationBarScreens(
        route = "ranking",
        title = "Ranking",
        icon = R.drawable.icons_ranking
    )

    object Informations : NavigationBarScreens(
        route = "informations",
        title = "Informations",
        icon = R.drawable.icons_informations
    )

    object Profile : NavigationBarScreens(
        route = "profile",
        title = "Profile",
        icon = R.drawable.icons_profile
    )
}
