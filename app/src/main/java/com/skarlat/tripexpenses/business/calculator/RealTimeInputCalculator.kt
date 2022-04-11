package com.skarlat.tripexpenses.business.calculator

interface RealTimeInputCalculator {

    companion object {
        const val CALCULATION_ERROR = -1
    }

    fun calculate(expression: String): Int
}