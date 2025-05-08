package com.example.ultimate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ultimate.presentation.auth.LoginScreen
import com.example.ultimate.presentation.auth.LoginScreenViewModel
import com.example.ultimate.presentation.home.HomeScreen
import com.example.ultimate.presentation.splash.SplashScreen


@Composable
fun AppNavigation(loginViewModel: LoginScreenViewModel) {
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
            HomeScreen()
        }
        composable(
            route = Screens.Login.route,
        ) {
            // Login screen
            LoginScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }

    }

}

