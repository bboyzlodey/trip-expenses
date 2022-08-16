package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.DebtorItem
import com.skarlat.tripexpenses.ui.model.Debtor
import com.skarlat.tripexpenses.ui.theme.defaultPaddingTop
import com.skarlat.tripexpenses.ui.viewModel.ExpenseViewModel
import com.skarlat.tripexpenses.utils.MockHelper

@Composable
fun ExpenseInfoScreen(viewModel: ExpenseViewModel) {
    val expenseInfo by viewModel.expenseInfo.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.expense_app_bar_title))
        }, actions = {
            IconButton(onClick = viewModel::onDeleteExpenseClicked) {
                Icon(
                    imageVector = Icons.Default.DeleteForever, contentDescription = stringResource(
                        id = R.string.delete_expense
                    )
                )
            }
        })
    }) {
        ExpenseInfo(
            description = expenseInfo.description,
            debtors = expenseInfo.debtors,
            totalAmount = expenseInfo.amount,
            onDebtPaidClicked = viewModel::markDebtAsPaid
        )
    }

}

@Composable
fun ExpenseInfo(
    description: String,
    debtors: List<Debtor>,
    totalAmount: Int,
    onDebtPaidClicked: (String) -> Unit
) {
    Box() {
        val scrollableState = rememberScrollableState(consumeScrollDelta = { it })
        Column(
            modifier = Modifier
                .scrollable(state = scrollableState, orientation = Orientation.Vertical)
                .padding(all = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.description_expense))
            Text(text = description, modifier = Modifier.padding(top = 8.dp))
            Row(defaultPaddingTop) {
                Text(text = stringResource(id = R.string.total_sum))
                Text(text = "$totalAmount RUB", modifier = Modifier.padding(start = 3.dp))
            }
            Text(text = stringResource(id = R.string.debtors), modifier = defaultPaddingTop)
            Spacer(modifier = Modifier.height(6.dp))
            debtors.forEach {
                DebtorItem(item = it) { onDebtPaidClicked.invoke(it.id) }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewExpenseInfo() {
    ExpenseInfo(
        description = "Посиделки в кафе",
        debtors = MockHelper.debtors,
        totalAmount = MockHelper.debtors.sumOf { it.amount },
        onDebtPaidClicked = {})
}