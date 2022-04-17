package com.skarlat.tripexpenses.business.calculation.input

interface ExpressionReader {
    fun readExpression(expr: String): String
}