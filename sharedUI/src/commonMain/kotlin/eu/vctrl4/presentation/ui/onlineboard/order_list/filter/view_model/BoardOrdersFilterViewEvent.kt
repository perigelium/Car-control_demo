package eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model

import eu.vctrl4.business.core.*

sealed class BoardOrdersFilterViewEvent: ViewEvent
{
    object OnFilterSubmitted : BoardOrdersFilterViewEvent()
    object OnFilterInvoked: BoardOrdersFilterViewEvent()
}