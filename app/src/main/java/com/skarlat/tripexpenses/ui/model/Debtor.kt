package com.skarlat.tripexpenses.ui.model

data class Debtor(
    val participant: Participant,
    val amount: Int,
    val isPayed: Boolean,
    val id: String
)
