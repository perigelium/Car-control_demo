package eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*


data class BoardOrdersFilterViewState(
    //val ordersRequest: OrderReportRequest? = OrderReportRequest(RightsOfUserId = null),
    val companies:List<Company> = listOf(),
	val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState