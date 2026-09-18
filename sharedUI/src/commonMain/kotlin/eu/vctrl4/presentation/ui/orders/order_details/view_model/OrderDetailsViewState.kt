package eu.vctrl4.presentation.ui.orders.order_details.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*


data class OrderDetailsViewState(
    val orderDetails: OrderDetails = OrderDetails(),
    val vehicleTypes:List<VehicleType> = listOf(),
    val vTypeOptionTypes: List<VehicleOption> = listOf(),
    val vehicleCapacityClasses: List<VehicleCapacityClass> = listOf(),
    val titleTexts: List<IdNameValueName> = listOf(),
    val titleTextsTranspTop: List<IdNameValueName> = listOf(),
    val titleTextsTranspOptionTypes: List<IdNameValueName> = listOf(),
    val titleTextsTranspBottom: List<IdNameValueName> = listOf(),
    val order: Order = Order(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState
