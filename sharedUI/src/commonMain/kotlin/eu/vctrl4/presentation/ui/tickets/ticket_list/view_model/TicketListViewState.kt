package eu.vctrl4.presentation.ui.tickets.ticket_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.presentation.utils.OrderUtils.prepareDefaultTicketsRequest

data class TicketListViewState(
	var firstStart: Boolean = true,
	var ordersRequest: OrderListRequest = prepareDefaultTicketsRequest(),
	var items : List<Order> = ArrayList(),

	val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState
