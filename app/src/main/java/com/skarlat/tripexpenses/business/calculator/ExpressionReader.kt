package com.skarlat.tripexpenses.business.calculator

interface ExpressionReader {
    fun readExpression(expr: String): String
}