package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import com.skarlat.tripexpenses.business.calculation.input.ExpressionReaderImpl
import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculator
import com.skarlat.tripexpenses.business.calculation.input.RealTimeInputCalculatorImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CompositeDistributionItemViewModel @Inject constructor() :
    ViewModel() {
    init {
        Timber.tag("VIEW_MODEL_INSTANCE").d("$this")
    }

    private val realTimeInputCalculator: RealTimeInputCalculator =
        RealTimeInputCalculatorImpl(ExpressionReaderImpl())
    private val inputMutableFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val distributionSum = inputMutableFlow.debounce(TimeUnit.SECONDS.toMillis(1L))
        .distinctUntilChanged()
        .map { realTimeInputCalculator.calculate(it) }

    fun onInputChanged(input: String) {
        Timber.tag("VIEW_MODEL_INSTANCE").d("${this}.onInputChanged()")
        inputMutableFlow.tryEmit(input)
    }
}