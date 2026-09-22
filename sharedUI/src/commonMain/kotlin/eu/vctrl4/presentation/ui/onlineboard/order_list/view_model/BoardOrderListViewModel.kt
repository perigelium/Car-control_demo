package eu.vctrl4.presentation.ui.onlineboard.order_list.view_model

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.storage.entities.OrderListRequest
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*

class BoardOrderListViewModel(
	private val boardOrderListUseCase: BoardOrderListUseCase,
	private val prefsStoreManager: PrefsStoreManagerImpl
                             ) :
	BaseViewModel<BoardOrderListEvent, BoardOrderListViewState, BoardOrderListAction>() {
	override fun setInitialState() = BoardOrderListViewState()

	fun viewIsReady() {
		requestBoardOrderList()
	}

	override fun onTriggerEvent(event: BoardOrderListEvent) {
		when (event) {
			is BoardOrderListEvent.FilterDialogSubmitted -> {
				setState { copy(ordersRequest = event.ordersRequest) }
				requestBoardOrderList()
				display { UIComponent.Toast(Res.string.disabled_in_demo_mode.asState) }
			}

			is BoardOrderListEvent.SearchOrderInvoked -> {
				onQueryOrderNumberSubmit(queryString = event.orderNumber)
			}

			is BoardOrderListEvent.OnUpdateNetworkState -> {
				if (event.networkState == NetworkState.Failed) display {
					UIComponent.Toast(
						Res.string.error_check_internet_connection.asState
					                 )
				}
			}
		}
	}

	private fun requestBoardOrderList() {
		executeUseCase(
			boardOrderListUseCase.execute(params = state.value.ordersRequest),
		               onSuccess = { resp ->
			               if (resp == null) {
				               setState { copy(items = listOf()) }
				               display {
					               UIComponent.DialogTitleText(
						               "", Res.string.error_no_orders_matching_parameters.asState
					                                          )
				               }
			               } else {
				               if (resp.isNotEmpty()) {
					               setState { copy(firstStart = false) }
				               } else {
					               if (state.value.firstStart) {
						               display { UIComponent.DialogMsg(JAlertResponse(Res.string.no_orders_found_matching_the_current_parameters_use_a_filter.asState)) }
					               }
				               }
				               setState { copy(items = resp) }
			               }
		               },
		               onLoading = {
			               setState { copy(progressBarState = it) }
		               },
		               onNetworkStatus = {
			               setEvent(BoardOrderListEvent.OnUpdateNetworkState(it))
		               })
	}

	private fun onQueryOrderNumberSubmit(queryString: String) {
		if (queryString.length > 3) {
			val request = OrderListRequest()
			if (queryString.all { it.isDigit() }) {
				request.NumberOrTicket = queryString

				//requestOrders(request)

				val order =
					state.value.items.firstOrNull() { it.OrderNumber?.contains(queryString) == true }

				if (order == null) {
					display { UIComponent.Toast(Res.string.nothing_found.asState) }
				} else {
					setState { copy(items = listOf(order)) }
				}
			}
		} else {
			display { UIComponent.DialogMsg(JAlertResponse(Res.string.error_enter_at_least_4_digits.asState)) }
		}
	}
}