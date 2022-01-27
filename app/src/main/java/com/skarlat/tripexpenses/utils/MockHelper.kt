package com.skarlat.tripexpenses.utils

import com.skarlat.tripexpenses.ui.model.Debtor
import com.skarlat.tripexpenses.ui.model.Participant
import kotlin.random.Random

object MockHelper {
    fun getParticipants() = listOf(
        Participant(id = Const.SELF_ID, name = "Я"),
        Participant(name = "Василий"),
        Participant(name = "Илья")
    )

    val debtors = getParticipants().map {
        Debtor(
            id = it.id,
            participant = it,
            amount = Random.nextInt(from = 100, until = 1000),
            isPayed = Random.nextBoolean()
        )
    }
}