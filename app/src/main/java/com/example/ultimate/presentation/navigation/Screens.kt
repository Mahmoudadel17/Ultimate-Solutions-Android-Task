package com.example.ultimate.presentation.navigation

sealed class Screens(val route:String){
    data object Splash : Screens(route = "splash")
    data object Login : Screens(route = "login")
    data object Home : Screens(route = "home")
}