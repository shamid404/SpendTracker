package com.codewithfk.expensetracker.android.feature.transactionlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codewithfk.expensetracker.android.feature.home.TransactionItem
import com.codewithfk.expensetracker.android.utils.Utils
import com.codewithfk.expensetracker.android.viewmodel.HomeViewModel

@Composable
fun TransactionListScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.expenses.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(state.value) { item ->
                val icon = Utils.getItemIcon(item)
                TransactionItem(
                    title = item.title,
                    amount = item.amount.toString(),
                    icon = icon!!,
                    date = item.date,
                    color = if (item.type == "Income") Color.Green else Color.Red
                )
            }
        }
    }
}
