package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.skarlat.tripexpenses.ui.component.ExpenseItem
import com.skarlat.tripexpenses.ui.model.Expense

@Composable
fun ExpenseListScreen(expenses: List<Expense>, onClick: (Expense) -> Unit) {
    LazyColumn {
        items(expenses) { item ->
            ExpenseItem(item = item) { onClick.invoke(item) }
            Divider()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(expenses = listOf(
        Expense(amount = "1000 рублей", debt = "-100 руб", isPayed = false, id = "", date = "26 февраля"),
        Expense(amount = "16789 рублей", debt = "-1100 руб", isPayed = true, id = "", date = "25 февраля"),
        Expense(amount = "14 рублей", debt = "0 руб", isPayed = true, id = "", date = "24 февраля"),
        Expense(amount = "500 рублей", debt = "-70 руб", isPayed = false, id = "", date = "23 февраля"),
        Expense(amount = "50 рублей", debt = "-50 руб", isPayed = true, id = "", date = "22 февраля"),
    ), onClick = {})
}