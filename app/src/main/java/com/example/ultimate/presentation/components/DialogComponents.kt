package com.example.ultimate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.ultimate.utils.Constants


@Composable
fun LanguageDialog(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onApplyClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(R.string.choose_language), style = MaterialTheme.typography.titleMedium) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LanguageOption(
                    languageTitle = stringResource(R.string.arabic),
                    languageName = stringResource(R.string.arabic_ar),
                    flagEmoji = "\uD83C\uDDEA\uD83C\uDDEC", // Egypt flag 🇪🇬
                    isSelected = selectedLanguage == Constants.LANGUAGE_AR_CODE,
                    onClick = { onLanguageSelected(Constants.LANG_AR) }
                )
                LanguageOption(
                    languageTitle = stringResource(R.string.english),
                    languageName = stringResource(R.string.english),
                    flagEmoji = "\uD83C\uDDEC\uD83C\uDDE7", // UK flag 🇬🇧
                    isSelected = selectedLanguage == Constants.LANGUAGE_EN_CODE,
                    onClick = { onLanguageSelected(Constants.LANG_EN) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.apply))
            }

        }
    )
}

@Composable
fun LanguageOption(
    languageTitle: String,
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
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = flagEmoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(text = languageTitle, style = MaterialTheme.typography.bodyMedium)
                Text(text = languageName, style = MaterialTheme.typography.bodySmall)

            }
        }
    }
}
