package eu.vctrl4.presentation.ui.onlineboard.order_list.view_model

import eu.vctrl4.business.core.*

sealed class BoardOrderListAction: ViewSingleAction
{
    data class ShowDialogWithMessage(val strMsg: String): BoardOrderListAction()
}