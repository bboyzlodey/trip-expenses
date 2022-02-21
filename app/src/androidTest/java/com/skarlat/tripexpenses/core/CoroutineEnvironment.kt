package com.skarlat.tripexpenses.core

import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executor

interface CoroutineEnvironment {
    val executor: Executor
    fun launchTest(testBlock: suspend CoroutineScope.() -> Unit)
}