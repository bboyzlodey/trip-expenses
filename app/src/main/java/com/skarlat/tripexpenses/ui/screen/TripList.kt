package com.skarlat.tripexpenses.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R
import com.skarlat.tripexpenses.ui.component.TripItem
import com.skarlat.tripexpenses.ui.model.Trip
import com.skarlat.tripexpenses.ui.theme.Shapes
import com.skarlat.tripexpenses.ui.viewModel.TripListViewModel

@Composable
fun TripList(trips: List<Trip>, onClick: (Trip) -> Unit, onCreateTripCLicked: () -> Unit) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = onCreateTripCLicked,
            shape = Shapes.small.copy(topEnd = CornerSize(10.dp))
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_trip)
            )
        }
    }) {
        Box() {
            LazyColumn {
                items(trips) { trip ->
                    TripItem(item = trip) { onClick.invoke(trip) }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun TripListScreen(viewModel: TripListViewModel) {
    val tripList by viewModel.tripListFlow.collectAsState(emptyList())
    TripList(
        trips = tripList,
        onClick = { viewModel.onTripCLicked(it.id) },
        onCreateTripCLicked = viewModel::onCreateTripClicked
    )
    BackHandler() {
        viewModel.onBackPressed()
    }
}

@Preview
@Composable
fun PreviewTripList() {
    TripList(
        trips = listOf(
            Trip("", "Санкт Петербург"),
            Trip("", "Архыз"),
            Trip("", "Домбай"),
            Trip("", "Эльбрус"),
            Trip("", "Шарегеш"),
            Trip("", "Краснодар"),
            Trip("", "Псков")
        ),
        onClick = {},
        onCreateTripCLicked = {}
    )
}