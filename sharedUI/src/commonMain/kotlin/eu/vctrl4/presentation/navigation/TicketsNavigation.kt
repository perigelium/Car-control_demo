package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface TicketsNavigation
{
    @Serializable
    data object OrdersFilter : TicketsNavigation

    @Serializable
    data object OrderList : TicketsNavigation

	@Serializable
	data class TicketConfirmationScreen(val strOrder: String) : TicketsNavigation

	@Serializable
	data class RateAndSubmitTicketDialog(val strTicket: String, val approveOrDecline: Boolean) : TicketsNavigation
}