package com.example.ultimate.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ultimate.R
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val billsState = viewModel.billsState.value
    val selectedTab = viewModel.selectedTab.value


    Column(modifier = Modifier.fillMaxSize()) {
        // Header with user info
        UserHeader(viewModel)

        // Tab Row
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { viewModel.selectTab(0) },
                text = { Text(stringResource(R.string.new_tab)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { viewModel.selectTab(1) },
                text = { Text(stringResource(R.string.others_tab)) }
            )
        }

        // Content based on state
        when (billsState) {
            is BillsState.Loading -> LoadingState()
            is BillsState.Empty -> EmptyState()
            is BillsState.Error -> ErrorState(billsState.message)
            is BillsState.Success -> BillsList(
                bills = viewModel.getFilteredBills(),
                getStatusName = { viewModel.getStatusName(it) }
            )
        }
    }
}

@Composable
private fun UserHeader(viewModel: HomeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = viewModel.getDeliveryName(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        // Add user avatar or other header elements if needed
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.List,
            contentDescription = "No orders",
            modifier = Modifier.size(64.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
                    text = "No orders yet",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You don't have any orders in your history.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Error,
            contentDescription = "Error",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Error loading orders",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
           //  viewModel.loadBills(viewModel.getDeliveryName())
        }) {
            Text("Retry")
        }
    }
}

@Composable
private fun BillsList(
    bills: List<DeliveryBill>,
    getStatusName: (String) -> String?
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bills) { bill ->
            BillItem(bill = bill, getStatusName = getStatusName)
        }
    }
}

@Composable
private fun BillItem(
    bill: DeliveryBill,
    getStatusName: (String) -> String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "#${bill.billNumber}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Status", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = getStatusName(bill.deliveryStatusFlag) ?: "Unknown",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column {
                    Text("Total price", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = "${bill.totalAmount} LE",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column {
                    Text("Date", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = bill.billDate,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Handle order details */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Order Details")
            }
        }
    }
}