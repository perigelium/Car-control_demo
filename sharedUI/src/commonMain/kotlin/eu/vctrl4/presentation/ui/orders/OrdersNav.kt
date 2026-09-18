package eu.vctrl4.presentation.ui.orders


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.navigation.*
import eu.vctrl4.presentation.ui.*
import eu.vctrl4.presentation.ui.orders.order_details.*
import eu.vctrl4.presentation.ui.orders.order_details.view_model.*
import eu.vctrl4.presentation.ui.orders.order_list.*
import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.*
import eu.vctrl4.presentation.ui.orders.order_list.view_model.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.ui.custom_views.composable.*
import eu.vctrl4.ui.orders.*
import eu.vctrl4.ui.orders.order_list.filter.*
import eu.vctrl4.ui.orders.order_list.filter.view_model.*
import eu.vctrl4.ui.orders.order_list.order_history.*
import org.koin.compose.*
import org.koin.compose.viewmodel.*

@Composable
fun OrdersNav(viewModel: OrderListViewModel, onMenuBtnClick: () -> Unit) {
	val navigator = rememberNavController()

	NavHost(
		startDestination = OrdersNavigation.OrderList,
		navController = navigator,
		modifier = Modifier.fillMaxSize()
	       ) {
		composable<OrdersNavigation.OrderList> {

			DefaultScreenWrap(
				errors = viewModel.errors,
				progressBarState = viewModel.state.value.progressBarState,
				screenContent = {
					OrderListScreen(
						items = viewModel.state.value.items,
						onFilterClick = {
							navigator.navigate(OrdersNavigation.OrdersFilter)
						},
						onSearchClick = {
							viewModel.onTriggerEvent(
								OrderListEvent.SearchOrderInvoked(it)
							                        )
						},
						onOrderSelected = {
							it?.let { orderId ->
								navigator.navigate(
									OrdersNavigation.OrderDetails(
										orderId, null
									                             )
								                  )
							}
						},
						topAppBar = {
							TopAppBarNavDrawer(
								title = Res.string.order_management.asState,
								itemsTotalLine = "${viewModel.state.value.itemsCountTotal} ${Res.string.orders_total.asState}",
								onMenuBtnClick = onMenuBtnClick
							                  )
						},
						onScrolledToEnd = { viewModel.onTriggerEvent(OrderListEvent.OnScrolledToEnd) },
						onActionInvoked = {
							it.OrderId?.apply {
								navigator.navigate(OrdersNavigation.OrderHistory(this))
							}
						}, // selectAction(it)
						onFabClick = {
							viewModel.onTriggerEvent(OrderListEvent.NewOrderInvoked) // onNewOrderInvoked()
						},
					               )
				})
		}

		composable<OrdersNavigation.OrdersFilter> {
			val filterViewModel: OrdersFilterViewModel = koinInject()

			LaunchedEffect(Unit) {
				filterViewModel.onTriggerEvent(OrdersFilterViewEvent.OnFilterInvoked)
			}

			OrderListFiltersDialog(
				companies = filterViewModel.state.value.custCompanies,
				ordersRequest = viewModel.state.value.ordersRequest,
				onSubmit = {
					viewModel.onTriggerEvent(OrderListEvent.FilterDialogSubmitted(it))
					navigator.popBackStack()
				},
				suppDepartments = filterViewModel.state.value.suppDepartments,
				onDismiss = { navigator.popBackStack() },
			                      )
		}

		composable<OrdersNavigation.CancelOrder> { backStackEntry ->

			val argument = backStackEntry.toRoute<OrdersNavigation.CancelOrder>()

			CancelOrderDialog(
				title = Res.string.are_you_sure_you_want_to_cancel_your_order.asState,
				onDismiss = { navigator.popBackStack() },
				onSubmit = {
					viewModel.onTriggerEvent(OrderListEvent.CancelOrder(argument.orderId, it))
					navigator.popBackStack()
				})
		}

		composable<OrdersNavigation.OrderHistory> { backStackEntry ->

			val viewModel: OrderHistoryViewModel = koinInject()

			val argument = backStackEntry.toRoute<OrdersNavigation.OrderHistory>()

			LaunchedEffect(Unit) {
				viewModel.onTriggerEvent(OrderHistoryEvent.RequestOrderHistory(argument.orderId))
			}

			OrderHistoryDialog(
				dialogTitle = Res.string.order_change_history.asState,
				listItems = viewModel.state.value.items,
				onDismiss = { navigator.popBackStack() })
		}

		composable<OrdersNavigation.OrderDetails> { backStackEntry ->

			val orderDetailsViewModel: OrderDetailsViewModel = koinViewModel()

			val argument = backStackEntry.toRoute<OrdersNavigation.OrderDetails>()

			if (argument.strAction == null) {
				LaunchedEffect(Unit) {

					orderDetailsViewModel.onTriggerEvent(OrderDetailsEvent.RequestOrder(argument.orderId))

					/*                argument.strAction?.let {
					viewModel.onTriggerEvent(OrderListEvent.PerformAction(it))
				}*/
				}
			}

			OrderDetailsNav(orderDetailsViewModel, argument.strAction, popUp = {
				if (it) viewModel.onTriggerEvent(
					OrderListEvent.FilterDialogSubmitted(
						OrderUtils.prepareDefaultOrdersRequest(true)
					                                    )
				                                ) else navigator.popBackStack()
			})
		}
	}
}
