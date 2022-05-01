package com.skarlat.tripexpenses

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skarlat.tripexpenses.ui.component.AutoCalculableDistributionItem
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
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() /*,ComponentActivity()*/ {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComposableContent()
        listenDialogData()
    }

    private fun setComposableContent() {
        setContent {
            TripExpensesTheme {
                Column() {
                    AutoCalculableDistributionItem()
                    Spacer(modifier = Modifier.height(16.dp))
                    AutoCalculableDistributionItem()
                }
                /*Column {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.weight(1f)
                    ) {
                        val navController = rememberNavController()
                        ListenNavigationEvents(navController = navController)
                        InitNavigationDestinations(navController = navController)
                    }
                    AppVersion()
                }*/
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
