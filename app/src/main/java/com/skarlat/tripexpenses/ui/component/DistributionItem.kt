package com.skarlat.tripexpenses.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ControlPointDuplicate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.model.Distribution
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.ui.model.chipItem
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.MockHelper

@Composable
fun DistributionItem(
    item: Distribution,
    onRemoveClicked: () -> Unit,
    participants: List<Participant>,
    isSupportDeleting: Boolean,
    allChipItemId: String = Const.ALL_ID,
    onDistributionValueChanged: (String) -> Unit,
    onDuplicateClicked: () -> Unit
) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            val cost = remember {
                item.cost
            }
            OutlinedTextField(
                value = cost.value.toString(),
                onValueChange = {
                    onDistributionValueChanged.invoke(it)
                    item.cost.value = it.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            val selectedItems = remember {
                item.participantIds
            }
            ChipGroup(
                items = participants.map { it.chipItem },
                selectedItems = selectedItems,
                onItemSelected = { itemId: String ->
                    if (!selectedItems.removeAll { itemId == allChipItemId || it == itemId }) {
                        if (itemId == allChipItemId) {
                            selectedItems.addAll(participants.map { it.id })
                        } else {
                            selectedItems.add(itemId)
                        }
                    }
                }
            )
            TextButton(
                onClick = {
                    selectedItems.clear()
                    selectedItems.addAll(participants.map { it.id })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(text = stringResource(id = R.string.select_all))
            }
        }

        IconButton(onClick = onDuplicateClicked) {
            Icon(
                imageVector = Icons.Default.ControlPointDuplicate,
                contentDescription = stringResource(
                    id = R.string.duplicate
                )
            )
        }
        if (isSupportDeleting) {
            IconButton(onClick = onRemoveClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun Preview() {
    val participants = MockHelper.getParticipants()
    val distItem = Distribution(
        id = 0,
        cost = mutableStateOf(0),
        participantIds = mutableStateListOf()
    )
    DistributionItem(
        item = distItem,
        onRemoveClicked = { /*TODO*/ },
        participants = participants,
        isSupportDeleting = false,
        onDistributionValueChanged = {},
        onDuplicateClicked = {}
    )
}