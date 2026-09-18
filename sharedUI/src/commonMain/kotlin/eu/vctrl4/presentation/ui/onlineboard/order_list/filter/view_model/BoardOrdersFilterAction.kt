package eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model

import eu.vctrl4.business.core.*

sealed class BoardOrdersFilterAction: ViewSingleAction
{
    object Popup: BoardOrdersFilterAction()
}