package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.ParticipantItem
import com.skarlat.tripexpenses.ui.model.Participant
import java.util.*

@Composable
fun CreateTripScreen(
    tripName: String,
    onTripNameChanged: (String) -> Unit,
    participants: List<Participant>,
    onAddParticipantClicked: () -> Unit,
    onRemoveParticipantClicked: (Participant) -> Unit,
    onParticipantNameChanged: (name: String, participant: Participant) -> Unit,
    onCreateTripClicked: () -> Unit
) {
    LazyColumn(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        item {
            TextField(value = tripName,
                onValueChange = onTripNameChanged,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = {
                    Text(text = stringResource(id = R.string.trip_name))
                })
        }
        items(participants) { item: Participant ->
            Spacer(modifier = Modifier.height(16.dp))
            ParticipantItem(
                item = item,
                onRemoveItemClicked = { onRemoveParticipantClicked.invoke(item) },
                onParticipantNameChanged = onParticipantNameChanged
            )
        }
        item {
            Button(
                onClick = { onAddParticipantClicked.invoke() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.add_participant))
            }
        }
        item {
            Button(
                onClick = { onCreateTripClicked.invoke() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.create_trip))
            }
        }
    }
}

@Preview
@Composable
fun PreviewCreateTripScreen() {
    var tripName by remember {
        mutableStateOf("")
    }
    var participants by remember {
        mutableStateOf(listOf<Participant>())
    }

    CreateTripScreen(
        tripName = tripName,
        onTripNameChanged = { tripName = it },
        participants = participants,
        onAddParticipantClicked = {
            participants = participants + Participant(UUID.randomUUID().toString(), "")
        },
        onRemoveParticipantClicked = { removeCandidate: Participant ->
            participants = participants.mapNotNull { if (it.id == removeCandidate.id) null else it }
        },
        onParticipantNameChanged = { newName: String, participant: Participant ->
            participants =
                participants.map { if (it.id == participant.id) it.copy(name = newName) else it }
        },
        onCreateTripClicked = {}
    )
}