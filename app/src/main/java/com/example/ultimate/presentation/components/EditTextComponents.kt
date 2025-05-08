package com.example.ultimate.presentation.components




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ultimate.R

@Composable
fun textFieldColors() : TextFieldColors {

    return TextFieldDefaults.colors(
        // text color
        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
        errorTextColor = MaterialTheme.colorScheme.onSecondary,

        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
        // Indicator color
        cursorColor = MaterialTheme.colorScheme.primary,
        // icon color
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
        errorTrailingIconColor = MaterialTheme.colorScheme.secondary,

        )
}




@Composable
fun PasswordEditText(
    password:String ,
    modifier: Modifier = Modifier,
    editTextHeight: Int = 60,
    onValueChange:(String) -> Unit,
) {
    Column {

        TextField(
            placeholder = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = stringResource(R.string.password), fontSize = 16.sp,color = MaterialTheme.colorScheme.onSecondary)
                }

                },
            value = password,
            visualTransformation = PasswordVisualTransformation() ,
            onValueChange = {
                onValueChange(it)
            },
            shape = RoundedCornerShape(24.dp),
            colors =  textFieldColors(),
            modifier = modifier
                .height(editTextHeight.dp)
                .fillMaxWidth()
                .shadow(elevation = 0.dp),
            singleLine = true,
            )

    }

}




@Composable
fun UserIDEditText(
    userId:String ,
    modifier: Modifier = Modifier,
    editTextHeight: Int = 60,
    onValueChange:(String) -> Unit) {
    Column {
        TextField(
            placeholder = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = stringResource(R.string.user_id), fontSize = 16.sp,color = MaterialTheme.colorScheme.onSecondary)

                } },
            value = userId,
            onValueChange = {
                onValueChange(it)
            },
            colors = textFieldColors(),
            shape = RoundedCornerShape(24.dp),
            modifier = modifier
                .height(editTextHeight.dp)
                .fillMaxWidth()
                .shadow(elevation = 0.dp),
            singleLine = true,
        )
    }
}



