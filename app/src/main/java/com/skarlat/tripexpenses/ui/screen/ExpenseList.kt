package com.skarlat.tripexpenses.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.ExpenseItem
import com.skarlat.tripexpenses.ui.model.Expense
import com.skarlat.tripexpenses.ui.model.TripCalculationResultModel
import com.skarlat.tripexpenses.ui.viewModel.ExpensesListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ExpenseList(
    expenses: List<Expense>,
    onClick: (Expense) -> Unit,
    participants: List<String>,
    totalAmount: Int,
    isCalculating: Boolean = false,
    calculationResultModel: TripCalculationResultModel? = null
) {
    LazyColumn {
        if (isCalculating) item {
            Text(text = stringResource(id = R.string.trip_calculation_in_process))
        }
        if (calculationResultModel != null) item {
            val context = LocalContext.current

            LaunchedEffect(key1 = calculationResultModel.title, block = {
                withContext(Dispatchers.IO) {
                    val repoDir = File(context.filesDir, "calc_reports")
                    repoDir.mkdir()
                    val file = File(repoDir, "calculation_result.txt")
                    file.writer().apply {
                        write(calculationResultModel.title)
                        appendLine()
                        calculationResultModel.balances.forEach {
                            appendLine(it)
                        }
                        close()
                    }
                }
            })

            Button(onClick = {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.type = "text/plain"
                intent.data = FileProvider.getUriForFile(
                    context,
                    "com.skarlat.tripexpenses",
                    File(File(context.filesDir, "calc_reports"), "calculation_result.txt"),
                    "reports"
                )
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.startActivity(intent)
            }) {
                Text(text = stringResource(id = R.string.open_trip_calculation))
            }
        }
        item {
            Text(
                text = stringResource(id = R.string.participants),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                participants.forEach {
                    Text(text = it, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Text(
                text = stringResource(id = R.string.total_paid_amount),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 4.dp, bottom = 16.dp),
                text = "$totalAmount рублей"
            )
        }
        items(expenses) { item ->
            ExpenseItem(item = item) { onClick.invoke(item) }
            Divider()
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ExpenseListScreen(viewModel: ExpensesListViewModel) {
    val expenses by viewModel.expensesList.collectAsState()
    val tripInfo by viewModel.tripInfo.collectAsState()
    val isCalculatingBalance by viewModel.isTripBalanceCalculating.collectAsState()
    val calculationReport by viewModel.tripBalanceCalculationResult.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onCreateExpenseClicked() }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_expense)
            )
        }
    }, topBar = {
        TopAppBar(title = {
            Text(text = tripInfo.name)
        }, actions = {
            IconButton(onClick = { viewModel.onCalculateBalanceClicked() }) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = stringResource(id = R.string.calculate_balance_description)
                )
            }
        })
    }) {
        ExpenseList(
            expenses = expenses,
            onClick = { viewModel.onExpenseClicked(it.id) },
            participants = tripInfo.participantsName,
            totalAmount = tripInfo.totalAmount,
            isCalculating = isCalculatingBalance,
            calculationResultModel = calculationReport
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
                date = "26 февраля",
                description = "Кафе"
            ),
            Expense(
                amount = "16789 рублей",
                debt = "-1100 руб",
                isPayed = true,
                id = "",
                date = "25 февраля",
                description = "Магазин Кант"
            ),
            Expense(
                amount = "14 рублей",
                debt = "0 руб",
                isPayed = true,
                id = "",
                date = "24 февраля",
                description = "МакДоналдс"
            ),
            Expense(
                amount = "500 рублей",
                debt = "-70 руб",
                isPayed = false,
                id = "",
                date = "23 февраля",
                description = "Ростикс"
            ),
            Expense(
                amount = "50 рублей",
                debt = "-50 руб",
                isPayed = true,
                id = "",
                date = "22 февраля",
                description = "Пиццерия IL Patio"
            ),
        ), onClick = {},
        participants = listOf("Василий", "Петя", "Leonid", "Valeria"),
        totalAmount = 0
    )
}