package com.skarlat.tripexpenses

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skarlat.tripexpenses.ui.navigation.NavigationEvent
import com.skarlat.tripexpenses.ui.navigation.Navigator
import com.skarlat.tripexpenses.ui.screen.CreateExpenseScreen
import com.skarlat.tripexpenses.ui.screen.CreateTripScreen
import com.skarlat.tripexpenses.ui.screen.ExpenseListScreen
import com.skarlat.tripexpenses.ui.screen.TripListScreen
import com.skarlat.tripexpenses.ui.theme.TripExpensesTheme
import com.skarlat.tripexpenses.ui.viewModel.CreateExpenseViewModel
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
        setContent {
            TripExpensesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
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
