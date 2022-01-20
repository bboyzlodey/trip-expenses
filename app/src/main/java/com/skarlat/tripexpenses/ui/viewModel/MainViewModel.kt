package com.skarlat.tripexpenses.ui.viewModel

import androidx.lifecycle.ViewModel
import com.skarlat.tripexpenses.ActivityUtils
import com.skarlat.tripexpenses.utils.DialogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val activityUtils: ActivityUtils
) : ViewModel() {

    val dialogDate: Flow<DialogData> get() = activityUtils.dialogData
}