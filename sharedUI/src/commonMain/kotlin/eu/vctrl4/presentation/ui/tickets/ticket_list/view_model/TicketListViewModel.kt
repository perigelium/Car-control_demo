package eu.vctrl4.presentation.ui.tickets.ticket_list.view_model

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.PORTION_OF_ORDERS_PER_PAGE
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogMsg
import eu.vctrl4.business.core.UIComponent.DialogTitleText
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.storage.entities.OrderListRequest
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*


class TicketListViewModel(
	private val orderListUseCase: OrderListUseCase) :
	BaseViewModel<TicketListEvent, TicketListViewState, TicketListAction>() {
	fun viewIsReady() {
		requestTicketList()
	}

	override fun setInitialState(): TicketListViewState = TicketListViewState()

	override fun onTriggerEvent(event: TicketListEvent) {
		when (event) {
			is TicketListEvent.FilterDialogSubmitted -> {
				setState { copy(ordersRequest = event.orderListRequest) }
				requestTicketList()
				display { UIComponent.Toast(Res.string.disabled_in_demo_mode.asState) }
			}

			is TicketListEvent.SearchOrderInvoked -> {
				onQueryOrderNumberSubmit(queryString = event.orderNumber)
			}

			is TicketListEvent.OnUpdateNetworkState -> {
				if (event.networkState == NetworkState.Failed) display {
					Toast(
						Res.string.error_check_internet_connection.asState
					     )
				}
			}
			else -> {}
		}
	}

	private fun requestTicketList(
		orderListRequest: OrderListRequest = state.value.ordersRequest,
		resetList: Boolean = true,
		isListSearch: Boolean = false
	                             ) {
		if (resetList) {
			orderListRequest.Offset = 0
			setState { copy(items = listOf()) }
		} else if (isListSearch) {
			orderListRequest.Offset += PORTION_OF_ORDERS_PER_PAGE
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
					setState { copy(items = listOf()) }

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

				val ticketsWithNumbers = resp.filter { !it.Ticket?.Number.isNullOrEmpty() }

				if (resetList) {
					setState { copy(items = ticketsWithNumbers) }
				} else if (isListSearch) {
					if (resp.isNotEmpty()) {
						setState { copy(items = ticketsWithNumbers) }
					} else {
						display {
							DialogTitleText(
								Res.string.nothing_found.asState,
								Res.string.prompt_change_search_parameters.asState
							               )
						}
					}
				} else {
					setState { copy(items = state.value.items + ticketsWithNumbers) }
				}
			}
		}, onLoading = {
			setState { copy(progressBarState = it) }
		}, onNetworkStatus = {
			setEvent(TicketListEvent.OnUpdateNetworkState(it))
		})
	}

	private fun onQueryOrderNumberSubmit(queryString: String) {
		if (queryString.isBlank()) {
			requestTicketList()
		} else if (queryString.length > 3) {
			val isDigitsOnly = queryString.all { it.isDigit() }

			if (isDigitsOnly) {
				val searchRequest = OrderListRequest()
				searchRequest.NumberOrTicket = queryString

				val order =
					state.value.items.firstOrNull() { it.Ticket?.Number?.contains(queryString) == true }

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