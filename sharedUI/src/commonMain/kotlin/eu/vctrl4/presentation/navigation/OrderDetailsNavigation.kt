package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface OrderDetailsNavigation
{
    @Serializable
    data object OrderDetails : OrderDetailsNavigation

    @Serializable
    data class CancelOrder(val orderId: String) : OrderDetailsNavigation
}