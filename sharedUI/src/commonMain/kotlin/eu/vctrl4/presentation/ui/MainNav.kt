package eu.vctrl4.presentation.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import androidx.navigation.*
import androidx.navigation.compose.*
import eu.vctrl4.presentation.ui.logout.*
import eu.vctrl4.presentation.ui.onlineboard.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.view_model.*
import eu.vctrl4.presentation.ui.orders.*
import eu.vctrl4.presentation.ui.orders.order_list.view_model.*
import eu.vctrl4.presentation.ui.tickets.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.view_model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.compose.viewmodel.*

@Composable
fun MainNav(
    navController: NavHostController, startDestination: String, drawerState: DrawerState, onCleanupUserSessionCompleted: () -> Unit,
)
{
    val coroutineScope = rememberCoroutineScope()

    fun onMenuBtnClick()
    {
        coroutineScope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    NavHost(
        navController = navController, startDestination = startDestination) {

        composable(route = "nav_online_board") {
            val viewModel: BoardOrderListViewModel = koinViewModel()

            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            DisposableEffect(Unit) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START)
                    {
                        viewModel.viewIsReady()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

            OnlineBoardNav(
                viewModel = viewModel,
                onMenuBtnClick = { onMenuBtnClick() })
        }

        composable("nav_orders") {

            val viewModel: OrderListViewModel = koinViewModel()

            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START)
                    {
                        viewModel.viewIsReady()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

	        OrdersNav(
		        viewModel = viewModel, onMenuBtnClick = { onMenuBtnClick() })
        }

        composable("nav_tickets") {

            val viewModel: TicketListViewModel = koinViewModel()

            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            DisposableEffect(Unit) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START)
                    {
                        viewModel.viewIsReady()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }

	        TicketsNav(
		        viewModel = viewModel, onMenuBtnClick = { onMenuBtnClick() })
        }

/*        composable(route = "nav_stats") {
            val statsPresentation = koinInject<StatsPresentation>()
            statsPresentation.StatsScreen(drawerState = drawerState)
        }*/

        composable(route = "nav_logout") {
            val viewModel: LogoutViewModel = koinViewModel()
            viewModel.setEvent(LogoutViewEvent.CleanUpUserSession)

            LaunchedEffect(Unit) {
                delay(500L)
                viewModel.action.onEach { effect ->
                    when (effect) {
                        LogoutViewAction.CleanUpUserSessionCompleted -> {
                            onCleanupUserSessionCompleted()
                        }
                    }
                }.collect {}
            }
        }
    }
}