package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.model.Debtor
import com.skarlat.tripexpenses.ui.model.Participant

@Composable
fun DebtorItem(item: Debtor, onDebtorPayedClicked: () -> Unit) {
    Row() {
        Surface(
            border = BorderStroke(2.dp, if (item.isPayed) Color.Green else Color.Magenta),
            modifier = Modifier.weight(1f)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(text = item.participant.name, Modifier.padding(start = 16.dp, top = 8.dp))
                Text(
                    text = "${item.amount} RUB",
                    modifier = Modifier
                        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .align(Alignment.End)
                )
            }
        }
        if (!item.isPayed) {
            IconButton(onClick = onDebtorPayedClicked) {
                Icon(
                    imageVector = Icons.Default.Paid,
                    contentDescription = stringResource(id = R.string.mark_debtor_as_payed)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDebtorItem() {
    LazyColumn(Modifier.padding(all = 16.dp)) {
        item {
            DebtorItem(
                item = Debtor(
                    participant = Participant("Василий"),
                    amount = 1000,
                    isPayed = false,
                    id = ""
                ), {}
            )
        }
        item {
            Spacer(Modifier.height(16.dp))
        }
        item {
            DebtorItem(
                item = Debtor(
                    participant = Participant("Денис"),
                    amount = 1000,
                    isPayed = true,
                    id = ""
                ), {}
            )
        }
    }
}