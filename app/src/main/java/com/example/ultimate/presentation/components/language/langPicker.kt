package com.example.ultimate.presentation.components.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ultimate.R
import com.example.ultimate.presentation.components.LanguageDialog
import com.example.ultimate.presentation.navigation.Screens

@Composable
fun LanguagePickerIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    isHome:Boolean = false,
    navController: NavController = rememberNavController()

) {
    val viewModel: LanguageViewModel = hiltViewModel()

    // Trigger dialog when user clicks on image
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_language),
            contentDescription = "Background decoration",
            modifier = modifier
                .clickable { viewModel.showDialog(true)
                },
            colorFilter = ColorFilter.tint(color)

        )

        if (viewModel.isDialogVisible.value) {
            LanguageDialog(
                selectedLanguage = if (viewModel.selectedLanguage.value == "1") "ar" else "en",
                onLanguageSelected = { viewModel.selectLanguage(it) },
                onApplyClick = { viewModel.applyLanguage{
                  if (isHome){
                      navController.navigate(Screens.Home.route) {
                          popUpTo(Screens.Login.route) { inclusive = true }
                      }
                  }
                } },
                onDismiss = { viewModel.showDialog(false) }
            )
        }
    }
}
