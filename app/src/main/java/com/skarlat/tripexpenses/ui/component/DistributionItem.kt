package com.skarlat.tripexpenses.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
    onDistributionValueChanged: (String) -> Unit
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
                            if (selectedItems.size == participants.size - 1)
                                selectedItems.add(allChipItemId)
                        }
                    }
                }
            )
        }
        if (isSupportDeleting) {
            IconButton(onClick = { onRemoveClicked.invoke() }) {
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
        onDistributionValueChanged = {}
    )
}