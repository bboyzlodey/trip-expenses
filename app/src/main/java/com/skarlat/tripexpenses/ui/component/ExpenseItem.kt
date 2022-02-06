package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.ui.model.Expense

@Composable
fun ExpenseItem(item: Expense, onClick: () -> Unit) {
    Box(Modifier.background(if (item.isPayed) Color.White else Color.Yellow)) {
        Box(
            Modifier
                .padding(8.dp)
                .clickable { onClick.invoke() }
                .fillMaxWidth()) {
            Column(Modifier.fillMaxWidth()) {
                val horizontalEnd = Modifier.align(
                    Alignment.End
                )
                Text(text = item.date)
                Text(
                    text = item.amount,
                    modifier = horizontalEnd
                )
                /*Text(
                    text = item.debt,
                    color = if (item.isPayed) Color.Green else Color.Red,
                    modifier = horizontalEnd
                )*/
            }
        }
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    ExpenseItem(
        item = Expense(
            id = "",
            amount = "1000 руб",
            debt = "-100 руб.",
            isPayed = false,
            date = "27 февраля 2018г."
        )
    ) { }
}