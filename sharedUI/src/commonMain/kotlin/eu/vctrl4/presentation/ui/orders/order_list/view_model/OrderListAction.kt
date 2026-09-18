package eu.vctrl4.presentation.ui.orders.order_list.view_model

import eu.vctrl4.business.core.*

sealed class OrderListAction: ViewSingleAction
{
    data class ShowDialogWithMessage(val strMsg: String): OrderListAction()
    class ShowOrdersHistory(val strOrdersHistory:String): OrderListAction()
}