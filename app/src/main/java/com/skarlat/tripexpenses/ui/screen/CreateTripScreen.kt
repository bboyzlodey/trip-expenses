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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.ParticipantItem
import com.skarlat.tripexpenses.ui.model.Participant
import com.skarlat.tripexpenses.ui.viewModel.CreateTripViewModel

@Composable
fun CreateTrip(
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
        item {
            Text(
                text = stringResource(id = R.string.partipants_of_trip),
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            )
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

@Composable
fun CreateTripScreen(viewModel: CreateTripViewModel) {
    val tripName by remember {
        viewModel.tripName
    }
    val participants = remember {
        viewModel.participants
    }
    CreateTrip(
        tripName = tripName,
        onTripNameChanged = viewModel::onTripNameChanged,
        participants = participants,
        onAddParticipantClicked = viewModel::onAddParticipantClicked,
        onRemoveParticipantClicked = { removeCandidate: Participant ->
            viewModel.onRemoveParticipantClicked(removeCandidate.id)
        },
        onParticipantNameChanged = { newName: String, participant: Participant ->

            viewModel.onParticipantNameChanged(id = participant.id, name = newName)
        },
        onCreateTripClicked = viewModel::onCreateTripClicked
    )
}

@Preview
@Composable
fun PreviewCreateTripScreen() {
    val viewModel = CreateTripViewModel()
    CreateTripScreen(viewModel = viewModel)
}