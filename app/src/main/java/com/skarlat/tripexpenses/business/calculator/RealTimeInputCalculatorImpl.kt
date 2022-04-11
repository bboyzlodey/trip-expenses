package com.skarlat.tripexpenses.business.calculator

import com.skarlat.tripexpenses.business.calculator.RealTimeInputCalculator.Companion.CALCULATION_ERROR

class RealTimeInputCalculatorImpl : RealTimeInputCalculator {
    override fun calculate(expression: String): Int {
        return CALCULATION_ERROR
    }
}