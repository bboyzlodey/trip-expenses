package com.skarlat.tripexpenses.business.calculation.input

import javax.inject.Inject

class ExpressionReaderImpl @Inject constructor() : ExpressionReader {

    override fun readExpression(expr: String): String {
        return filterExpression(expr)
    }

    private fun filterExpression(expr: String): String {
        var lastFoundOperator: Char? = null
        val stringBuilder = StringBuilder(expr.length)
        var prevValueType = SymbolType.NONE
        expr.forEach { symbol ->
            val currentValueType = symbol.valueType ?: return@forEach
            when {
                (prevValueType == SymbolType.NONE || prevValueType == SymbolType.DIGIT) && currentValueType == SymbolType.DIGIT ->
                    stringBuilder.append(symbol)
                prevValueType == SymbolType.OPERATOR && currentValueType == SymbolType.DIGIT ->
                    lastFoundOperator?.let { operator ->
                        stringBuilder.append(' ')
                        stringBuilder.append(operator)
                        stringBuilder.append(' ')
                        stringBuilder.append(symbol)
                    }
                prevValueType == SymbolType.NONE -> {
                    lastFoundOperator = null
                    return@forEach
                }
                currentValueType == SymbolType.OPERATOR -> lastFoundOperator = symbol
            }
            prevValueType = currentValueType
        }
        return stringBuilder.toString()
    }

    private val Char.valueType: SymbolType?
        get() = when {
            isDigit() -> SymbolType.DIGIT
            Operator.values().any { it.symbol == this } -> SymbolType.OPERATOR
            else -> null
        }

    private enum class Operator(val symbol: Char) {
        PLUS('+'),
        MINUS('-'),
        DIV('/'),
        MULTI('*'),
    }

    private enum class SymbolType {
        OPERATOR,
        NONE,
        DIGIT
    }
}