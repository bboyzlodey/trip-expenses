package com.skarlat.tripexpenses.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest

open class CoroutineEnvironmentImpl : CoroutineEnvironment {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    override val executor = testDispatcher.asExecutor()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun launchTest(testBlock: suspend CoroutineScope.() -> Unit) =
        testScope.runBlockingTest(testBlock)
}