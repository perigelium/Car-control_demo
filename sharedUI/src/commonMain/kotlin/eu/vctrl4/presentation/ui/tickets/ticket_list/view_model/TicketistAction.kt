package eu.vctrl4.presentation.ui.tickets.ticket_list.view_model

import eu.vctrl4.business.core.*

sealed class TicketListAction: ViewSingleAction
{
    data class ShowDialogWithMessage(val strMsg: String): TicketListAction()
}