package com.skarlat.tripexpenses.utils

import com.skarlat.tripexpenses.ui.model.Participant

object MockHelper {
    fun getParticipants() = listOf(
        Participant(id = Const.SELF_ID, name = "Я"),
        Participant(name = "Василий"),
        Participant(name = "Илья")
    )
}