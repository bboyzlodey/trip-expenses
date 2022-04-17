package com.skarlat.tripexpenses.business.calculation.input

import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculator.Companion.CALCULATION_ERROR
import javax.inject.Inject

class RealTimeInputCalculatorImpl @Inject constructor(
    private val expressionReader: ExpressionReader
) : RealTimeInputCalculator {

    override fun calculate(expression: String): Int = kotlin.runCatching {
        executeExprTool(formattedExpr = expressionReader.readExpression(expression))
    }.getOrNull() ?: CALCULATION_ERROR

    private fun executeExprTool(formattedExpr: String): Int {
        val proccess = Runtime.getRuntime().exec("expr $formattedExpr")
        val result = proccess.inputStream.reader().readText().trim()
        return result.toInt()
    }
}