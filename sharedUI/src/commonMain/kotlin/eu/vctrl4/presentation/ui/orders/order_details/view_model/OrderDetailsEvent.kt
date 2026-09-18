package eu.vctrl4.presentation.ui.orders.order_details.view_model

import eu.vctrl4.business.core.*

sealed class OrderDetailsEvent: ViewEvent
{
    data class RequestOrder(val orderId:String) : OrderDetailsEvent()
    data class CancelOrder(val strComment:String?): OrderDetailsEvent()
    data class OnUpdateNetworkState(val networkState: NetworkState) : OrderDetailsEvent()
}