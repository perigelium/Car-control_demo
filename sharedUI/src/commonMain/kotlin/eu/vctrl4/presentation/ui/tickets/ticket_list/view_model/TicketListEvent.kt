package eu.vctrl4.presentation.ui.tickets.ticket_list.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.OrderListRequest

sealed class TicketListEvent: ViewEvent
{
    class SearchOrderInvoked(val orderNumber: String): TicketListEvent()
    class FilterDialogSubmitted(val orderListRequest: OrderListRequest): TicketListEvent()
    object OnScrolledToEnd: TicketListEvent()

    data class OnUpdateNetworkState(val networkState: NetworkState) : TicketListEvent()
}