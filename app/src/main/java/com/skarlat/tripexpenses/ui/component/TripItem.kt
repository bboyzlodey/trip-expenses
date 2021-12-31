package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.ui.model.Trip

@Composable
fun TripItem(item: Trip, onClick: () -> Unit) {
    Box(
        Modifier
            .background(Color.White)
            .clickable(onClick = onClick, role = Role.Button)
            .fillMaxWidth()
    ) {
        Box(Modifier.padding(8.dp)) {
            Column {
                Text(text = item.name)
            }
        }
    }
}

@Preview
@Composable
fun PreviewTripItem() {
    TripItem(
        item = Trip(
            id = "",
            name = "Санкт Петербург"
        )
    ) {}
}