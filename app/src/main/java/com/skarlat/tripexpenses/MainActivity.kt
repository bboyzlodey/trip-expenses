package com.skarlat.tripexpenses

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skarlat.tripexpenses.ui.screen.CreateExpenseScreen
import com.skarlat.tripexpenses.ui.screen.PreviewCreateTripScreen
import com.skarlat.tripexpenses.ui.screen.PreviewExpenseListScreen
import com.skarlat.tripexpenses.ui.screen.PreviewTripList
import com.skarlat.tripexpenses.ui.theme.TripExpensesTheme
import com.skarlat.tripexpenses.ui.viewModel.MainViewModel
import com.skarlat.tripexpenses.utils.Const
import com.skarlat.tripexpenses.utils.DialogFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() /*,ComponentActivity()*/ {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripExpensesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Const.SCREEN_LIST_TRIP
                    ) {
                        composable(route = Const.SCREEN_LIST_TRIP) { navBackStackEntry ->
                            PreviewTripList()
                        }
                        composable(route = Const.SCREEN_CREATE_TRIP) { navBackStackEntry ->
                            PreviewCreateTripScreen()
                        }
                        composable(route = Const.SCREEN_LIST_EXPENSES) { navBackStackEntry ->
                            PreviewExpenseListScreen()
                        }
                        composable(route = Const.SCREEN_CREATE_EXPENSE) { navBackStackEntry ->
                            CreateExpenseScreen(viewModel = hiltViewModel())
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
