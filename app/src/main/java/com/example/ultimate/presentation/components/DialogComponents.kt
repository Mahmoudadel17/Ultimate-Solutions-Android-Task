package com.example.ultimate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ultimate.R




@Composable
fun LanguageDialog(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onApplyClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Choose Language", style = MaterialTheme.typography.titleMedium) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LanguageOption(
                    languageCode = "ar",
                    languageName = "العربية",
                    flagEmoji = "\uD83C\uDDEA\uD83C\uDDEC", // Egypt flag 🇪🇬
                    isSelected = selectedLanguage == "ar",
                    onClick = { onLanguageSelected("1") }
                )
                LanguageOption(
                    languageCode = "en",
                    languageName = "English",
                    flagEmoji = "\uD83C\uDDEC\uD83C\uDDE7", // UK flag 🇬🇧
                    isSelected = selectedLanguage == "en",
                    onClick = { onLanguageSelected("2") }
                )
            }
        },
        confirmButton = {
            Button(onClick = onApplyClick) {
                Text("Apply")
            }
        }
    )
}

@Composable
fun LanguageOption(
    languageCode: String,
    languageName: String,
    flagEmoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFD0F0C0) else Color.LightGray.copy(alpha = 0.2f)
    val borderColor = if (isSelected) Color.Green else Color.Transparent

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = flagEmoji, fontSize = 24.sp)
            Text(text = languageName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
