package com.example.ultimate.utils

import androidx.navigation.NavHostController
import com.example.ultimate.presentation.navigation.Screens
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val pref: SharedPreferences,
) {
    private var lastInteractionTime: Long = System.currentTimeMillis()
    private val timeout = 2 * 60 * 1000L // 2 minutes in milliseconds

    fun updateLastInteraction() {
        lastInteractionTime = System.currentTimeMillis()
        pref.setSharedPreferences(Constants.SESSION_TIME,System.currentTimeMillis().toString())
    }

    fun checkSession(navController: NavHostController) {
        if (System.currentTimeMillis() - lastInteractionTime > timeout) {
            logout(navController)
        }
    }
    fun hasSessionExpired(): Boolean {
        val savedTime = pref.getSharedPreferences(Constants.SESSION_TIME, "0").toLong()
        val now = System.currentTimeMillis()
        val elapsedMinutes = (now - savedTime) / 1000 / 60
        return elapsedMinutes >= 2
    }
    fun clearSession(){
        // Clear any session data
        pref.clear()

    }

    private fun logout(navController: NavHostController) {
        clearSession()
        // Navigate to login screen
        navController.navigate(Screens.Login.route) {
            popUpTo(0) // Clear back stack
        }
    }
}