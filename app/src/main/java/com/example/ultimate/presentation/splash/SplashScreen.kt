package com.example.ultimate.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ultimate.R
import com.example.ultimate.presentation.navigation.Screens
import com.example.ultimate.ui.theme.SplashBackgroundColor
import kotlinx.coroutines.delay

// SplashScreen.kt
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds splash
        navController.navigate(Screens.Login.route) {
            popUpTo(Screens.Splash.route) { inclusive = true }
        }
    }

    DeliveryAppScreen()

}

@Composable
fun DeliveryAppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackgroundColor)
    ) {
        // This spacer will push the first box to center
        Spacer(modifier = Modifier.weight(1f))

        // Centered Box (first box)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.group_3915),
                contentDescription = "Company logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        // This spacer will push the second box to bottom
       // Spacer(modifier = Modifier.weight(1f))

        // Bottom Box (second box)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Gives more space to the bottom content
        ) {
            Image(
                painter = painterResource(id = R.drawable.path_1),
                contentDescription = "Background shape",
                modifier = Modifier
                    .fillMaxSize()
                    .padding( top = 10.dp)

            )

            Image(
                painter = painterResource(id = R.drawable.group_8),
                contentDescription = "Delivery truck",
                modifier = Modifier
                    .size(350.dp)
                    .padding(start = 60.dp, top = 70.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        DeliveryAppScreen()
    }
}