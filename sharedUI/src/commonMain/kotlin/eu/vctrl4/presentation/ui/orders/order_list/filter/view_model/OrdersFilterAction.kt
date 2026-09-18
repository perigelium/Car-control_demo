package eu.vctrl4.ui.orders.order_list.filter.view_model

import eu.vctrl4.business.core.*

sealed class OrdersFilterAction: ViewSingleAction
{
    object Popup: OrdersFilterAction()
}