package eu.vctrl4.presentation.ui.orders.order_list.filter.view_model

import eu.vctrl4.business.core.*

sealed class OrdersFilterViewEvent: ViewEvent
{
    object OnFilterSubmitted : OrdersFilterViewEvent()
    object OnFilterInvoked: OrdersFilterViewEvent()
    class OnShowMessage(val message:String): OrdersFilterViewEvent()
}