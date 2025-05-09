package com.example.ultimate.presentation.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ultimate.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.res.stringResource
import com.example.ultimate.presentation.components.PasswordEditText
import com.example.ultimate.presentation.components.UserIDEditText
import com.example.ultimate.presentation.components.language.LanguagePickerIcon
import com.example.ultimate.presentation.navigation.Screens


@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel ,
    navController: NavController
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box (
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp)
            ){
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.onxrestaurant_logo), // Add OnxRestaurant_Logo.svg
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(170.dp, 74.dp)
                        //.align(Alignment.Start)
                )

                Text(
                    text = stringResource(R.string.orders_delivery),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .padding(start = 85.dp,top = 50.dp)
                       // .align(alignment = Alignment.End)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box{
                // Background elements
                Image(
                    painter = painterResource(id = R.drawable.ic_circle), // Add delivery.svg to your drawables
                    contentDescription = "Background decoration",
                    modifier = Modifier
                        //.align(Alignment.Top)
                        .size(200.dp, 190.dp)
                        .padding(start = 20.dp)

                )
                LanguagePickerIcon(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(top = 70.dp, start = 60.dp)
                        .padding(start = 56.dp)

                )

            }


        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp,),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {


            Column  (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.welcome_back),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp, top = 20.dp)
                )

                Text(
                    text = stringResource(R.string.log_back_into_your_account),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary

                    ),
                    modifier = Modifier.padding(bottom = 40.dp)
                )

            }
            UserIDEditText(
                userId = state.userId,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) { viewModel.onUserIdChange(it)}


            PasswordEditText(
                password = state.password,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) { viewModel.onPasswordChange(it) }


            // Show More option
            TextButton(
                onClick = { /* Handle show more */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.show_more),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.login {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.log_in),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = state.isLoading,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            AnimatedVisibility(
                visible = !state.error.isNullOrEmpty(),
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                    ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }


            Image(
                painter = painterResource(id = R.drawable.delivery), // Add ic_circle.svg to your drawables
                contentDescription = "Red circle decoration",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(170.dp)
                    .padding(top = if (state.error.isNullOrEmpty()) 36.dp else 6.dp)
            )
        }
    }
}



