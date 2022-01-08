package com.skarlat.tripexpenses.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Distribution(
    val id: Int,
    val cost: MutableState<Int>,
    val participantIds: SnapshotStateList<String>
)
