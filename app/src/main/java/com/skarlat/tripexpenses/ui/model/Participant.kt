package com.skarlat.tripexpenses.ui.model

data class Participant(val name: String, val id: String = name)

val Participant.chipItem get() = ChipItem(name, id)
