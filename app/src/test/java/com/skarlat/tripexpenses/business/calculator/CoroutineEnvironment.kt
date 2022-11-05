package com.skarlat.tripexpenses.business.calculator

import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executor

interface CoroutineEnvironment {
    val executor: Executor
    fun launchTest(testBlock: suspend CoroutineScope.() -> Unit)
}