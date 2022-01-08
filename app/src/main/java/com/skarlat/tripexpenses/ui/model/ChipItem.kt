package com.skarlat.tripexpenses.ui.model

data class ChipItem(val name: String, val id: String = name)

val ChipItem.participant get() = Participant(name, id)
