package com.skarlat.tripexpenses

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skarlat.tripexpenses.ui.component.AppVersion
import com.skarlat.tripexpenses.ui.navigation.ExpenseDestination
import com.skarlat.tripexpenses.ui.navigation.NavigationEvent
import com.skarlat.tripexpenses.ui.navigation.Navigator
import com.skarlat.tripexpenses.ui.screen.*
import com.skarlat.tripexpenses.ui.theme.TripExpensesTheme
import com.skarlat.tripexpenses.ui.viewModel.CreateExpenseViewModel
import com.skarlat.tripexpenses.ui.viewModel.ExpenseViewModel
import com.skarlat.tripexpenses.ui.viewModel.ExpensesListViewModel
import com.skarlat.tripexpenses.ui.viewModel.MainViewModel
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.DialogFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() /*,ComponentActivity()*/ {

    private val viewModel by viewModels<MainViewModel>()
    private val intentFilter = IntentFilter("EXEC_COMMAND_1")
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val command = intent?.getStringExtra("command_bundle")
            Log.d("BC_COMMAND", "receiveCommand: $command")
            command?.let {
                lifecycleScope.launch {
                    launchCommand(it)
                }
            }
        }
    }

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComposableContent()
        listenDialogData()
    }

    private suspend fun launchCommand(command: String) {
        withContext(Dispatchers.IO) {
            val process = Runtime.getRuntime().exec(command)
            process.outputStream.flush()
            process.outputStream.close()
            val result = process.inputStream.reader().readText()
            Log.d("BC_COMMAND", "command: $command \nresult: $result")
        }
    }

    private fun setComposableContent() {
        setContent {
            TripExpensesTheme {
                Column {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.weight(1f)
                    ) {
                        val navController = rememberNavController()
                        ListenNavigationEvents(navController = navController)
                        InitNavigationDestinations(navController = navController)
                    }
                    AppVersion()
                }
            }
        }
    }

    @Composable
    private fun ListenNavigationEvents(navController: NavController) {
        LaunchedEffect(navController) {
            navigator.destinations.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateUp -> navController.navigateUp()
                    is NavigationEvent.Directions -> navController.navigate(
                        event.destination,
                        event.builder
                    )
                }
            }
        }
    }

    @Composable
    private fun InitNavigationDestinations(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Const.SCREEN_LIST_TRIP
        ) {
            composable(route = Const.SCREEN_LIST_TRIP) { navBackStackEntry ->
                TripListScreen(viewModel = hiltViewModel())
            }
            composable(route = Const.SCREEN_CREATE_TRIP) { navBackStackEntry ->
                CreateTripScreen(viewModel = hiltViewModel())
            }
            composable(route = ExpenseDestination.route()) { navBackStackEntry ->
                val viewModel = hiltViewModel<ExpenseViewModel>()
                val expenseId =
                    navBackStackEntry.arguments?.getString("expenseId") ?: ""
                LaunchedEffect(key1 = expenseId, block = {
                    viewModel.openExpense(expenseId)
                })
                ExpenseInfoScreen(viewModel = viewModel)
            }
            composable(route = Const.SCREEN_LIST_EXPENSES) { navBackStackEntry ->
                val viewModel = hiltViewModel<ExpensesListViewModel>()
                val tripId = navBackStackEntry.arguments?.getString("tripId") ?: ""
                LaunchedEffect(key1 = tripId, block = {
                    viewModel.openTripId(tripId)
                })
                ExpenseListScreen(viewModel)
            }
            composable(route = Const.SCREEN_CREATE_EXPENSE) { navBackStackEntry ->
                val viewModel = hiltViewModel<CreateExpenseViewModel>()
                val tripId = navBackStackEntry.arguments?.getString("tripId") ?: ""
                LaunchedEffect(key1 = tripId, block = {
                    viewModel.tripId = tripId
                })
                CreateExpenseScreen(viewModel = viewModel)
            }
        }
    }

    private fun listenDialogData() {
        lifecycleScope.launchWhenStarted {
            viewModel.dialogDate.collect { dialogData ->
                supportFragmentManager.let {
                    DialogFactory.showDialog(it, dialogData)
                }
            }
        }
    }
}
