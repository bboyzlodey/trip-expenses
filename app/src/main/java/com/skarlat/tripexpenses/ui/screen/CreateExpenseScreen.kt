package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.ChipGroup
import com.skarlat.tripexpenses.ui.component.DistributionItem
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.chipItem
import com.skarlat.tripexpenses.ui.viewModel.CreateExpenseViewModel

@Composable
fun CreateExpenseScreen(viewModel: CreateExpenseViewModel) {
    val participants by viewModel.participants.collectAsState()
    val distributions = remember {
        viewModel.distribution
    }
    LazyColumn(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        item {
            val description by viewModel.expenseDescription.collectAsState()
            Text(text = stringResource(id = R.string.description_expense))
            OutlinedTextField(
                value = description,
                onValueChange = viewModel::onExpenseDescriptionChanged,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
        item {
            val date by viewModel.expenseDate.collectAsState()
            Text(text = stringResource(id = R.string.date))
            TextButton(
                onClick = { viewModel.onSelectDateClicked() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = date)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            val costTotal by viewModel.expenseSummaryCost.collectAsState()
            Text(text = stringResource(id = R.string.total_sum))
            Text(text = "$costTotal RUB", modifier = Modifier.padding(start = 16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(text = stringResource(id = R.string.distribution))
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(distributions, key = { it.id }) { item: Distribution ->
            DistributionItem(
                item = item,
                onRemoveClicked = { viewModel.onRemoveDistributionCLicked(item) },
                participants = participants,
                isSupportDeleting = distributions.size > 1,
                onDistributionValueChanged = {
                    viewModel.onCostChanged(
                        costId = item.id.toString(),
                        cost = it
                    )
                }
            )
        }

        item {
            Button(onClick = { viewModel.onAddDistributionClicked() }) {
                Text(text = stringResource(id = R.string.add_distribution))
            }
        }
        item {
            Text(text = stringResource(id = R.string.pay_owner))
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            val payOwnerId by viewModel.payOwnerId.collectAsState()
            ChipGroup(
                items = participants.map { it.chipItem },
                selectedItems = listOf(payOwnerId),
                onItemSelected = { viewModel.onPayOwnerSelected(it) })
        }
        item {
            TextButton(onClick = { viewModel.onCreateExpenseClicked() }) {
                Text(text = stringResource(id = R.string.end))
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
//    CreateExpenseScreen(viewModel = CreateExpenseViewModel())
}
