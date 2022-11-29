package com.example.testparsingetcompose.ui.navigation

sealed class AppMainScreens(val route: String){
    object MainScreen : AppMainScreens("main")
    object ConnectionScreen : AppMainScreens("connection")
    object RegistrationScreen : AppMainScreens("registration")
}
