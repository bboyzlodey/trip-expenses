package com.skarlat.tripexpenses.data.local.model

interface ExpenseWithDebtors {
    val expenseId: String
    val debtors: List<ParticipantExpense>
    val payOwner: ParticipantId
}

class ParticipantExpense(val costAmount: Int, val participantId: ParticipantId)

@JvmInline
value class ParticipantId(val id: String)