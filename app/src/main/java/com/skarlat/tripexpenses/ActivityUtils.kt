package com.skarlat.tripexpenses

import com.skarlat.tripexpenses.utils.DialogData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@ActivityRetainedScoped
class ActivityUtils @Inject constructor() {

    val dialogData: Flow<DialogData> get() = dialogDataFlow
    private val dialogDataFlow = MutableSharedFlow<DialogData>(extraBufferCapacity = 1)

    suspend fun showDialog(data: DialogData) {
        dialogDataFlow.emit(data)
    }
}