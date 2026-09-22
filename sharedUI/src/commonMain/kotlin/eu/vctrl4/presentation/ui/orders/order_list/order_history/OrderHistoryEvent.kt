package eu.vctrl4.presentation.ui.orders.order_list.order_history

import eu.vctrl4.business.core.*

sealed class OrderHistoryEvent: ViewEvent
{
    data class RequestOrderHistory(val orderId:String) : OrderHistoryEvent()
    data class OnUpdateNetworkState(val networkState: NetworkState) : OrderHistoryEvent()
}