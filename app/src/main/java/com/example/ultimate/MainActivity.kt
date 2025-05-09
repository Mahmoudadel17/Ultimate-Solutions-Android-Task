package com.example.ultimate

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ultimate.presentation.auth.LoginScreenViewModel
import com.example.ultimate.presentation.components.language.LanguageViewModel
import com.example.ultimate.presentation.navigation.AppNavigation
import com.example.ultimate.ui.theme.UltimateTheme
import com.example.ultimate.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginScreenViewModel>()
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: LanguageViewModel = hiltViewModel()
            val context = LocalContext.current
            val resources = context.resources

            viewModel.applyLanguage(resources) {}
            UltimateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        loginViewModel,
                        sessionManager
                        )


                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        sessionManager.updateLastInteraction()
        return super.dispatchTouchEvent(ev)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        sessionManager.updateLastInteraction()
    }
}
