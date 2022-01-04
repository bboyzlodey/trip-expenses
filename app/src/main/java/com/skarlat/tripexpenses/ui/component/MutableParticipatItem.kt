package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.model.Participant

@Composable
fun ParticipantItem(
    item: Participant,
    onRemoveItemClicked: () -> Unit,
    onParticipantNameChanged: (name: String, participant: Participant) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = item.name,
                onValueChange = { value -> onParticipantNameChanged.invoke(value, item) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            IconButton(
                onClick = { onRemoveItemClicked.invoke() },
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = stringResource(id = R.string.remove_description)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewParticipantItem() {
    val participant = Participant(id = "", "Василий")
    Box(modifier = Modifier.fillMaxSize()) {
        ParticipantItem(
            item = participant,
            onRemoveItemClicked = { /*TODO*/ },
            onParticipantNameChanged = { name: String, participant: Participant -> })
    }
}