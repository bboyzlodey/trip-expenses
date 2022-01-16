package com.skarlat.tripexpenses

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.lifecycle.lifecycleScope
import com.skarlat.tripexpenses.ui.theme.TripExpensesTheme
import com.skarlat.tripexpenses.ui.viewModel.MainViewModel
import com.skarlat.tripexpenses.utils.DialogFactory
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() /*,ComponentActivity()*/ {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripExpensesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.dialogDate.collect { dialogData ->
                supportFragmentManager.let {
                    DialogFactory.showDialog(it, dialogData)
                }
            }
        }
    }
}
