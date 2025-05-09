package com.example.ultimate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ultimate.presentation.auth.LoginScreen
import com.example.ultimate.presentation.auth.LoginScreenViewModel
import com.example.ultimate.presentation.home.HomeScreen
import com.example.ultimate.presentation.home.HomeViewModel
import com.example.ultimate.presentation.splash.SplashScreen
import com.example.ultimate.utils.SessionHandler
import com.example.ultimate.utils.SessionManager
import kotlinx.coroutines.delay


@Composable
fun AppNavigation(
    loginViewModel: LoginScreenViewModel,
    sessionManager: SessionManager
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
            val viewModel : HomeViewModel = hiltViewModel()
            // viewModel.refresh()

            HomeScreen(
                viewModel = viewModel,
                navController = navController
                )
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

    // Check session periodically
    LaunchedEffect(Unit) {
        while (true) {
            delay(30_000)
            sessionManager.checkSession(navController)
        }
    }

    SessionHandler(navController,sessionManager)

}

