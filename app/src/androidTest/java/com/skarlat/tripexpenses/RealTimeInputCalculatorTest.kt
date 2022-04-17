package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.business.calculation.input.ExpressionReaderImpl
import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculatorImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class RealTimeInputCalculatorTest {

    private val calculatorImpl = RealTimeInputCalculatorImpl(ExpressionReaderImpl())

    @Test
    fun simpleDigitTest() {
        val actualResult = 150
        val factResult = calculatorImpl.calculate("150")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleDigitWithWhiteSpaceTest() {
        val actualResult = 150
        val factResult = calculatorImpl.calculate("150 ")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleDigitWithWhiteSpacesTest_1() {
        val actualResult = 150
        val factResult = calculatorImpl.calculate(" 150 ")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleDigitWithWhiteSpacesTest_2() {
        val actualResult = 150
        val factResult = calculatorImpl.calculate(" \t150 \t \n")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleDigitWithWhiteSpacesTest_3() {
        val actualResult = 150
        val factResult = calculatorImpl.calculate("1 5\n0 \t \n")

        assertEquals(factResult, actualResult)
    }


    @Test
    fun simpleSumTest() {
        val actualResult = 333
        symmetricTest(
            firstArgument = "300",
            secondArgument = "33",
            operator = '+',
            actualResult = actualResult
        )
    }

    private fun symmetricTest(
        firstArgument: String,
        secondArgument: String,
        operator: Char,
        actualResult: Int
    ) {
        simpleOperatorTest(
            firstArgument = firstArgument,
            secondArgument = secondArgument,
            operator = operator,
            actualResult = actualResult
        )
        simpleOperatorTest(
            firstArgument = secondArgument,
            secondArgument = firstArgument,
            operator = operator,
            actualResult = actualResult
        )
    }

    private fun simpleOperatorTest(
        firstArgument: String,
        secondArgument: String,
        operator: Char,
        actualResult: Int
    ) {
        assertEquals(
            calculatorImpl.calculate("$firstArgument$operator$secondArgument"),
            actualResult
        )
        assertEquals(
            calculatorImpl.calculate("$firstArgument $operator$secondArgument"),
            actualResult
        )
        assertEquals(
            calculatorImpl.calculate("$firstArgument $operator $secondArgument"),
            actualResult
        )
        assertEquals(
            calculatorImpl.calculate(" $firstArgument $operator $secondArgument"),
            actualResult
        )
        assertEquals(
            calculatorImpl.calculate(" \n$firstArgument$operator\n$secondArgument"),
            actualResult
        )
        assertEquals(
            calculatorImpl.calculate("\t$firstArgument      $operator\n\n\t\r$secondArgument"),
            actualResult
        )
    }

    @Test
    fun simpleSumWithWhiteSpaceTest() {
        val actualResult = 333
        val factResult = calculatorImpl.calculate(" 300 + 3 3 ")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun simpleSubtractionTest() {
        val actualResult = 1500
        simpleOperatorTest(
            firstArgument = "2000",
            secondArgument = "500",
            operator = '-',
            actualResult = actualResult
        )
    }

    @Test
    fun simpleMultiplicationTest() {
        val actualResult = 48
        symmetricTest(
            firstArgument = "8",
            secondArgument = "6",
            operator = '*',
            actualResult = actualResult
        )
    }

    @Test
    fun simpleDivisionTest() {
        val actualResult = 8
        simpleOperatorTest(
            firstArgument = "64",
            secondArgument = "8",
            operator = '/',
            actualResult = actualResult
        )
    }

    @Test
    fun compositeSum() {
        val actualResult = 20
        val factResult = calculatorImpl.calculate("10+ 4  \t + 1+ \n +4+1 ")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeSub() {
        val actualResult = 317
        val factResult = calculatorImpl.calculate("340- 14  \t -\t 1- \n -\n4-1 -3")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeMult() {
        val actualResult = 1024
        val factResult = calculatorImpl.calculate("512 * 2* \t1")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun compositeDiv() {
        val actualResult = 3
        val factResult = calculatorImpl.calculate("81 /9\n/3")

        assertEquals(factResult, actualResult)
    }

    @Test
    fun composite_1() {
        val factResult = calculatorImpl.calculate("1500/2 + 300*3")
        val actualResult = 1650

        assertEquals(factResult, actualResult)
    }

    @Test
    fun composite_2() {
        val factResult = calculatorImpl.calculate("1500/2 + 301")
        val actualResult = 1051

        assertEquals(factResult, actualResult)
    }

    @Test
    fun composite_3_error() {
        val factResult = calculatorImpl.calculate("1500/2 + 301 +")
        val actualResult = 1051

        assertEquals(factResult, actualResult)
    }

    @Test
    fun composite_4_error() {
        val factResult = calculatorImpl.calculate("1500/")
        val actualResult = 1500

        assertEquals(factResult, actualResult)
    }

}