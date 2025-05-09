package com.example.ultimate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ultimate.R

@Composable
fun OrderTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Colors for different states
    val activeTabColor = MaterialTheme.colorScheme.primary
    val inactiveTabColor = MaterialTheme.colorScheme.background
    val activeTextColor = MaterialTheme.colorScheme.onPrimary
    val inactiveTextColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background),
            //.padding(4.dp),

        horizontalArrangement = Arrangement.SpaceAround

    ) {
        // New Tab
        TabItem(
            isSelected = selectedTab == 0,
            text = stringResource(R.string.new_tab),
            activeColor = activeTabColor,
            inactiveColor = inactiveTabColor,
            activeTextColor = activeTextColor,
            inactiveTextColor = inactiveTextColor,
            onClick = { onTabSelected(0) }
        )

        // Others Tab
        TabItem(
            isSelected = selectedTab == 1,
            text = stringResource(R.string.others_tab),
            activeColor = activeTabColor,
            inactiveColor = inactiveTabColor,
            activeTextColor = activeTextColor,
            inactiveTextColor = inactiveTextColor,
            onClick = { onTabSelected(1) }
        )
    }
}

@Composable
private fun TabItem(
    isSelected: Boolean,
    text: String,
    activeColor: Color,
    inactiveColor: Color,
    activeTextColor: Color,
    inactiveTextColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) activeColor else inactiveColor)
            .clickable { onClick() }
            .padding(horizontal = 28.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) activeTextColor else inactiveTextColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}