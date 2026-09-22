package eu.vctrl4.presentation.ui.orders.order_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.presentation.utils.OrderUtils.prepareDefaultOrdersRequest

data class OrderListViewState(
	var firstStart: Boolean = true,
	var ordersRequest: OrderListRequest = prepareDefaultOrdersRequest(false),
	var items : List<Order> = ArrayList(),
	var itemsCountTotal:Int = 0,

	val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState


