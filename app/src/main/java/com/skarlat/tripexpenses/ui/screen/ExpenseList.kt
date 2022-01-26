package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.ExpenseItem
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.viewModel.ExpensesListViewModel

@Composable
fun ExpenseList(expenses: List<Expense>, onClick: (Expense) -> Unit, participants: List<String>) {
    LazyColumn {
        item {
            Text(text = stringResource(id = R.string.participants))
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                participants.forEach {
                    Text(text = it, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        items(expenses) { item ->
            ExpenseItem(item = item) { onClick.invoke(item) }
            Divider()
        }
    }
}

@Composable
fun ExpenseListScreen(viewModel: ExpensesListViewModel) {
    val expenses by viewModel.expensesList.collectAsState()
    val tripInfo by viewModel.tripInfo.collectAsState()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onCreateExpenseClicked() }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_expense)
            )
        }
    }, topBar = {
        TopAppBar() {
            Text(text = tripInfo.name)
        }
    }) {
        ExpenseList(
            expenses = expenses,
            onClick = { viewModel.onExpenseClicked(it.id) },
            participants = tripInfo.participantsName
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseList(
        expenses = listOf(
            Expense(
                amount = "1000 рублей",
                debt = "-100 руб",
                isPayed = false,
                id = "",
                date = "26 февраля"
            ),
            Expense(
                amount = "16789 рублей",
                debt = "-1100 руб",
                isPayed = true,
                id = "",
                date = "25 февраля"
            ),
            Expense(
                amount = "14 рублей",
                debt = "0 руб",
                isPayed = true,
                id = "",
                date = "24 февраля"
            ),
            Expense(
                amount = "500 рублей",
                debt = "-70 руб",
                isPayed = false,
                id = "",
                date = "23 февраля"
            ),
            Expense(
                amount = "50 рублей",
                debt = "-50 руб",
                isPayed = true,
                id = "",
                date = "22 февраля"
            ),
        ), onClick = {},
        participants = listOf("Василий", "Петя", "Leonid", "Valeria")
    )
}