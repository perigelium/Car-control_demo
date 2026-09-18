package eu.vctrl4.presentation.ui.orders.order_list.view_model

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.PORTION_OF_ORDERS_PER_PAGE
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogMsg
import eu.vctrl4.business.core.UIComponent.DialogTitleText
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import eu.vctrl4.storage.entities.*


class OrderListViewModel(
	private val orderListUseCase: OrderListUseCase,
	private val ordersCountUseCase: OrdersCountUseCase
                        ) : BaseViewModel<OrderListEvent, OrderListViewState, OrderListAction>() {
	fun viewIsReady() {
		requestOrdersCount()
		requestOrderList()
	}

	override fun setInitialState(): OrderListViewState = OrderListViewState()

	override fun onTriggerEvent(event: OrderListEvent) {
		when (event) {
			is OrderListEvent.FilterDialogSubmitted -> {
				event.orderListRequest.Limit = PORTION_OF_ORDERS_PER_PAGE

				setState { copy(ordersRequest = event.orderListRequest) }
				requestOrdersCount()
				requestOrderList()
				display { UIComponent.Toast(Res.string.disabled_in_demo_mode.asState) }
			}

			is OrderListEvent.SearchOrderInvoked -> {
				onQueryOrderNumberSubmit(queryString = event.orderNumber)
			}

			is OrderListEvent.OnUpdateNetworkState -> {
				if (event.networkState == NetworkState.Failed) display {
					Toast(
						Res.string.error_check_internet_connection.asState
					     )
				}
			}

			OrderListEvent.OnScrolledToEnd -> {
				if (state.value.items.size > 2 && state.value.itemsCountTotal > state.value.items.size) {
					state.value.ordersRequest.Offset += PORTION_OF_ORDERS_PER_PAGE
					state.value.ordersRequest.Limit = minOf(
						PORTION_OF_ORDERS_PER_PAGE,
						state.value.itemsCountTotal - state.value.items.size
					                                       )
					requestOrderList(resetList = false)
				}
			}

			is OrderListEvent.CancelOrder -> {

			}

			OrderListEvent.ListResetInvoked -> {
				requestOrderList()
			}

			OrderListEvent.NewOrderInvoked -> {
				display { UIComponent.Toast(Res.string.disabled_in_demo_mode.asState) }
			}
		}
	}

	private fun requestOrdersCount() {
		val strRequest = AppJson.encodeToString(state.value.ordersRequest)
		val request = AppJson.decodeFromString<OrderListRequest>(strRequest)
		request.Limit = null
		request.Offset = 0

		executeUseCase(ordersCountUseCase.execute(params = request), onSuccess = { resp ->
			setState { copy(itemsCountTotal = resp ?: 0) }
		}, onLoading = {
			setState { copy(progressBarState = it) }
		}, onNetworkStatus = {
			setEvent(OrderListEvent.OnUpdateNetworkState(it))
		})
	}

	private fun requestOrderList(
		orderListRequest: OrderListRequest = state.value.ordersRequest,
		resetList: Boolean = true,
		isListSearch: Boolean = false
	                            ) {
		if (resetList) {
			orderListRequest.Offset = 0
			setState { copy(items = listOf(), itemsCountTotal = 0) }
		}

		executeUseCase(orderListUseCase.execute(params = orderListRequest), onSuccess = { resp ->
			if (resp.isNullOrEmpty()) {
				if (state.value.firstStart) {
					display {
						DialogMsg(
							JAlertResponse(
								Res.string.error_no_orders_matching_parameters.asState,
								Res.string.prompt_use_filter.asState
							              )
						         )
					}
				} else {
					display {
						DialogTitleText(
							Res.string.error_no_orders_matching_parameters.asState,
							Res.string.prompt_change_filter_or_search.asState
						               )
					}
				}
			} else {
				if (resp.isNotEmpty()) {
					state.value.firstStart = false
				}

				if (resetList) {
					setState { copy(items = resp) }
				} else if (isListSearch) {
					if (resp.isNotEmpty()) {
						setState { copy(items = resp) }
					} else {
						display {
							DialogTitleText(
								Res.string.nothing_found.asState,
								Res.string.prompt_change_search_parameters.asState
							               )
						}
					}
				} else {
					setState { copy(items = state.value.items + resp) }
				}
			}
		}, onLoading = {
			setState { copy(progressBarState = it) }
		}, onNetworkStatus = {
			setEvent(OrderListEvent.OnUpdateNetworkState(it))
		})
	}

	private fun onQueryOrderNumberSubmit(queryString: String) {
		if (queryString.length > 3) {
			val request = OrderListRequest()
			if (queryString.all { it.isDigit() }) {
				val searchRequest = OrderListRequest()
				if (queryString.all { it.isDigit() }) {
					request.Number = queryString
				}
				//requestOrderList(searchRequest)

				val order =
					state.value.items.firstOrNull() { it.Number?.contains(queryString) == true }

				if (order == null) {
					display { UIComponent.Toast(Res.string.nothing_found.asState) }
				} else {
					setState { copy(items = listOf(order)) }
				}
			}
		} else {
			display { DialogMsg(JAlertResponse(Res.string.error_enter_at_least_4_digits.asState)) }
		}
	}
}