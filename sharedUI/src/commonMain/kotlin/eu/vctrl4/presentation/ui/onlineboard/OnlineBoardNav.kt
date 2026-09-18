package eu.vctrl4.presentation.ui.onlineboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.navigation.*
import eu.vctrl4.presentation.ui.*
import eu.vctrl4.presentation.ui.base.*
import eu.vctrl4.presentation.ui.onlineboard.map.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.filter.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.view_model.*
import eu.vctrl4.storage.remote.entities.*
import eu.vctrl4.ui.online_board.map.view_model.*
import org.koin.compose.*
import org.koin.compose.viewmodel.*

@Composable
fun OnlineBoardNav(onMenuBtnClick: () -> Unit, viewModel: BoardOrderListViewModel) {
	val navigator = rememberNavController()

	NavHost(
		startDestination = OnlineBoardNavigation.OrderList,
		navController = navigator,
		modifier = Modifier.fillMaxSize()
	       ) {
		composable<OnlineBoardNavigation.OrderList> {

			DefaultScreenWrap(
				errors = viewModel.errors,
				progressBarState = viewModel.state.value.progressBarState,
				screenContent = {
					BoardOrderListScreen(
						state = viewModel.state.value,
						onFilterClick = {
							navigator.navigate(
								OnlineBoardNavigation.OrdersFilter
							                  )
						},
						onSearchClick = {
							viewModel.onTriggerEvent(
								BoardOrderListEvent.SearchOrderInvoked(it)
							                        )
						},
						onOrderSelected = {
							val jsonString = AppJson.encodeToString(it)
							navigator.navigate(OnlineBoardNavigation.VehicleTrackMap(orderJson = jsonString))
						},
						events = { viewModel.onTriggerEvent(it) },
						topAppBar = {
							TopAppBarNavDrawer(
								onMenuBtnClick = onMenuBtnClick,
								title = Res.string.online_board.asState,
								itemsTotalLine = "${viewModel.state.value.items.size} ${Res.string.orders_total.asState}"
							                  )
						},
					                                                                                         )
				},
			                 )
		}

		composable<OnlineBoardNavigation.OrdersFilter> { backStackEntry ->
			val filterViewModel: BoardOrdersFilterViewModel = koinInject()

			LaunchedEffect(Unit) {
				filterViewModel.onTriggerEvent(BoardOrdersFilterViewEvent.OnFilterInvoked)
			}

			BoardOrdersFiltersDialog(
				companies = filterViewModel.state.value.companies,
				request = viewModel.state.value.ordersRequest,
				onSubmit = {
					viewModel.onTriggerEvent(
						BoardOrderListEvent.FilterDialogSubmitted(
							it
						                                         )
					                        )
					navigator.popBackStack()
				},
				onDismiss = { navigator.popBackStack() })
		}


		composable<OnlineBoardNavigation.VehicleTrackMap> { backStackEntry ->
			val mapViewModel: VehicleTrackViewModel = koinViewModel()

			val route = backStackEntry.toRoute<OnlineBoardNavigation.VehicleTrackMap>()

			val orderBundle = AppJson.decodeFromString<BoardOrder>(route.orderJson)

			LaunchedEffect(Unit) {
				mapViewModel.onTriggerEvent(VehicleTrackEvent.InitWithArgs(orderBundle))
				mapViewModel.onTriggerEvent(VehicleTrackEvent.ViewIsReady)
			}

			DefaultScreenWrap(
				errors = mapViewModel.errors,
				progressBarState = mapViewModel.state.value.progressBarState,
				screenContent = {
					OsmMapScreen(
						viewState = mapViewModel.state.value,
						modifier = Modifier.fillMaxSize(),
						onBackClicked = { navigator.popBackStack() })
				})
		}
	}

}
