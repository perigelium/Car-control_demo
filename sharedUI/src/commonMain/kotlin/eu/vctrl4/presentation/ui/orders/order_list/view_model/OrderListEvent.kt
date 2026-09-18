package eu.vctrl4.presentation.ui.orders.order_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.storage.entities.*

sealed class OrderListEvent: ViewEvent
{
    class SearchOrderInvoked(val orderNumber: String): OrderListEvent()
    class FilterDialogSubmitted(val orderListRequest: OrderListRequest): OrderListEvent()
    object OnScrolledToEnd: OrderListEvent()
    object ListResetInvoked: OrderListEvent()
	object NewOrderInvoked: OrderListEvent()
    class CancelOrder(val orderId: String, val comment:String): OrderListEvent()

    data class OnUpdateNetworkState(val networkState: NetworkState) : OrderListEvent()
}