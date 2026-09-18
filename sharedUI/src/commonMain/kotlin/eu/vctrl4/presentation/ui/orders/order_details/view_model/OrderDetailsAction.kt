package eu.vctrl4.ui.orders.order_details.view_model

import eu.vctrl4.business.core.*

sealed class OrderDetailsAction: ViewSingleAction
{
    data class Navigate(val route: String): OrderDetailsAction()
    data class Popup(val reloadOrderList: Boolean): OrderDetailsAction()
}