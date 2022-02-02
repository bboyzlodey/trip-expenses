package com.skarlat.tripexpenses.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringResourceWrapper {
    fun getString(@StringRes res: Int, vararg arguments: Any): String
}

class StringResourceWrapperImpl @Inject constructor(@ApplicationContext private val context: Context) :
    StringResourceWrapper {
    override fun getString(res: Int, vararg arguments: Any): String {
        return if (arguments.isNotEmpty()) context.getString(res, arguments)
        else context.getString(res)
    }

}