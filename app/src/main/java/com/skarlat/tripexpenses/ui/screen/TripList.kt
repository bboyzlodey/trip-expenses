package com.skarlat.tripexpenses.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.skarlat.tripexpenses.ui.component.TripItem
import com.skarlat.tripexpenses.ui.model.Trip

@Composable
fun TripListScreen(trips: List<Trip>, onClick: (Trip) -> Unit) {
    LazyColumn {
        items(trips) { trip ->
            TripItem(item = trip) { onClick.invoke(trip) }
            Divider()
        }
    }
}

@Preview
@Composable
fun PreviewTripList() {
    TripListScreen(
        trips = listOf(
            Trip("", "Санкт Петербург"),
            Trip("", "Архыз"),
            Trip("", "Домбай"),
            Trip("", "Эльбрус"),
            Trip("", "Шарегеш"),
            Trip("", "Краснодар"),
            Trip("", "Псков")
        ),
        onClick = {}
    )
}