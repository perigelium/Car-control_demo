package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface OrdersNavigation
{
    @Serializable
    data object OrdersFilter : OrdersNavigation

    @Serializable
    data object OrderList : OrdersNavigation

	@Serializable
	data class OrderDetails(val orderId: String, val strAction:String?) : OrdersNavigation

	@Serializable
	data class OrderHistory(val orderId: String) : OrdersNavigation

	@Serializable
	data class CancelOrder(val orderId: String) : OrdersNavigation
}