package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface OnlineBoardNavigation {

    @Serializable
    data object OrdersFilter : OnlineBoardNavigation

    @Serializable
    data object OrderList : OnlineBoardNavigation

    @Serializable
    data class VehicleTrackMap(val orderJson: String) : OnlineBoardNavigation
}

