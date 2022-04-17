package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.business.calculation.input.ExpressionReader
import com.skarlat.tripexpenses.business.calculation.input.ExpressionReaderImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpressionReaderTest {

    private val expressionReader: ExpressionReader = ExpressionReaderImpl()

    @Test
    fun basicTest_1() {
        val actualResult = "100"
        val factResult = expressionReader.readExpression(actualResult)

        assertEquals(factResult, actualResult)
    }

    @Test
    fun basicTest_2() {
        val actualResult = "100"
        val factResult = expressionReader.readExpression("1 0 0   \t")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleTest_1() {
        val actualResult = "100 + 1"
        val factResult = expressionReader.readExpression(actualResult)

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeExpressionTest_1() {
        val actualResult = "900 + 1 + 2"
        val factResult = expressionReader.readExpression("  900\t  + 1 + 2")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeExpressionTest_2() {
        val actualResult = "900 + 1 + 2"
        val factResult = expressionReader.readExpression("  900\t  \t+  \n 1 + 2")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeExpressionTest_3() {
        val actualResult = "900 - 1 * 2"
        val factResult = expressionReader.readExpression("  9 0  0\t  \t+-  \n 1 ///+* 2")

        assertEquals(factResult, actualResult)
    }
}