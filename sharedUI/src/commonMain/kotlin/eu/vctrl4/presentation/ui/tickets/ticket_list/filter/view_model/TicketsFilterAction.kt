package eu.vctrl4.presentation.ui.tickets.ticket_list.filter.view_model

import eu.vctrl4.business.core.*

sealed class TicketsFilterAction: ViewSingleAction
{
    object Popup: TicketsFilterAction()
}