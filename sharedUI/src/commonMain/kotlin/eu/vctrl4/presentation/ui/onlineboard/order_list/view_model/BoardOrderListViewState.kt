package eu.vctrl4.presentation.ui.onlineboard.order_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.presentation.utils.OrderUtils.prepareDefaultReportRequest
import eu.vctrl4.storage.entities.*
import eu.vctrl4.storage.remote.entities.*

data class BoardOrderListViewState(
    val items: List<BoardOrder> = listOf(),
    val firstStart: Boolean = true,
    val ordersRequest: OrderReportRequest = prepareDefaultReportRequest(),
    val orderListRequest: OrderListRequest = OrderListRequest(),

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState