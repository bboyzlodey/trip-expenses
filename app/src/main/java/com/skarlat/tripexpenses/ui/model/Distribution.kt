package com.skarlat.tripexpenses.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Distribution(
    val id: Int,
    val cost: MutableState<Int>,
    val participantIds: SnapshotStateList<String>
) {
    companion object Factory {
        private var counter = 0

        fun create(cost: Int, participantIds: List<String>): Distribution {
            return Distribution(
                id = counter++,
                cost = mutableStateOf(cost),
                participantIds = mutableStateListOf(*participantIds.toTypedArray())
            )
        }

        fun createEmpty(): Distribution {
            return create(0, emptyList())
        }

        fun resetCounter() {
            counter = 0
        }
    }
}
