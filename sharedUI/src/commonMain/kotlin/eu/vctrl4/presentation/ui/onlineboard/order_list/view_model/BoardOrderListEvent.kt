package eu.vctrl4.presentation.ui.onlineboard.order_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.storage.remote.entities.*

sealed class BoardOrderListEvent: ViewEvent
{
    class SearchOrderInvoked(val orderNumber: String): BoardOrderListEvent()
    class FilterDialogSubmitted(val ordersRequest: OrderReportRequest): BoardOrderListEvent()

    data class OnUpdateNetworkState(val networkState: NetworkState) : BoardOrderListEvent()
}