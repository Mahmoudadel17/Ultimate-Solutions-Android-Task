package com.example.ultimate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ultimate.presentation.auth.LoginScreen
import com.example.ultimate.presentation.splash.SplashScreen


@Composable
fun AppNavigation(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Home.route) {
            // Home screen
        }
        composable(
            route = Screens.Login.route,
        ) {
            // Login screen
            LoginScreen()
        }

    }

}

