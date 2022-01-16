package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import com.skarlat.tripexpenses.utils.DialogData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class MainViewModel : ViewModel() {

    val dialogDate: Flow<DialogData> get() = dialogMutableSharedFlow
    private val dialogMutableSharedFlow = MutableSharedFlow<DialogData>(extraBufferCapacity = 1)

    suspend fun showDialog(dialogData: DialogData) {
        dialogMutableSharedFlow.emit(dialogData)
    }

}