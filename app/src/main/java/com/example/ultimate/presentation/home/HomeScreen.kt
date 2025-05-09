package com.example.ultimate.presentation.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ultimate.R
import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill
import com.example.ultimate.data.remote.dto.deliveryBills.getStatusColor
import com.example.ultimate.presentation.components.OrderTabs
import com.example.ultimate.presentation.components.language.LanguagePickerIcon
import com.example.ultimate.utils.Constants

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController

) {
    val billsState = viewModel.billsState.value
    val selectedTab = viewModel.selectedTab.value


    Column(modifier = Modifier.fillMaxSize()) {
        // Header with user info
        UserHeader(
            viewModel,
            navController
        )


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OrderTabs(
                selectedTab = selectedTab,
                onTabSelected = { viewModel.selectTab(it) },
                modifier = Modifier
                    .shadow(elevation = 36.dp)
                    .padding(horizontal = 0.dp, vertical = 8.dp)
            )
        }



        // Content based on state
        when (billsState) {
            is BillsState.Loading -> LoadingState()
            is BillsState.Empty -> EmptyState()
            is BillsState.Error -> ErrorState(billsState.message){viewModel.refresh()}
            is BillsState.Success -> BillsList(
                bills = viewModel.getFilteredBills(),
                getStatusName = { viewModel.getStatusName(it) }
            )
        }

    }
}

@Composable
private fun UserHeader(
    viewModel: HomeViewModel,
    navController: NavController

) {
    val (firstName, lastName) = viewModel.getDeliveryNames()



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = firstName.ifEmpty { stringResource(R.string.delivery_person) },
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = lastName.ifEmpty { "" },
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        // Add user avatar or other header elements if needed

        Spacer(modifier = Modifier.weight(1f))


        Box {
            // Background elements
            Image(
                painter = painterResource(id = R.drawable.ic_circle_2), // Add delivery.svg to your drawables
                contentDescription = "Background decoration",
                modifier = Modifier
                    .size(200.dp, 190.dp)
                    .padding(start = 57.dp)
            )
            LanguagePickerIcon(
                modifier = Modifier
                    .padding(top = 50.dp, start = 100.dp)
                    .padding(start = 56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .padding(2.dp)
                ,
                color = MaterialTheme.colorScheme.primary,
                isHome = true,
                navController = navController
            )
            // Background elements
            Image(
                painter = painterResource(id = R.drawable.deliveryman), // Add delivery.svg to your drawables
                contentDescription = "Background decoration",
                modifier = Modifier
                    .size(170.dp)
                    .padding(top = 20.dp, end = 10.dp)

            )
        }


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
        Image(
            painter = painterResource(id = R.drawable.ic_emptyorder), // Add ic_circle.svg to your drawables
            contentDescription = "empty circle decoration",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(170.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.no_orders_yet),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.you_don_t_have_any_orders_in_your_history),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    refresh:()->Unit
) {
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
            text = stringResource(R.string.error_loading_orders),
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
            refresh()
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
        item {
            Spacer(
                modifier = Modifier
                    .size(50.dp)
            )
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
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 8.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(16.dp)
            ) {
                Text(
                    text = "#${bill.billSerial}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Column {
                        Text("Status", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = getStatusName(bill.deliveryStatusFlag) ?:
                            if (bill.deliveryStatusFlag == "0") "New" else "Unknown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = bill.getStatusColor()
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


            }

            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .background(bill.getStatusColor()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 20.dp, end = 10.dp)
                )

                Text(
                    text = "Order",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary

                )
                // Background elements
                Image(
                    painter = painterResource(id = R.drawable.ic_back), // Add delivery.svg to your drawables
                    contentDescription = "Background decoration",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 20.dp, end = 10.dp)

                )

                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 20.dp, end = 10.dp)
                )

            }


        }
    }
}