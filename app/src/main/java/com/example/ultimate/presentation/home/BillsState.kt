package com.example.ultimate.presentation.home

import com.example.ultimate.data.remote.dto.deliveryBills.DeliveryBill

sealed class BillsState {
    object Loading : BillsState()
    object Empty : BillsState()
    data class Success(val bills: List<DeliveryBill>) : BillsState()
    data class Error(val message: String) : BillsState()
}