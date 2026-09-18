package eu.vctrl4.ui.orders.order_list.order_history

import eu.vctrl4.business.core.*
import eu.vctrl4.storage.remote.entities.*


data class OrderHistoryViewState(
    val items: List<OrderHistoryItem> = listOf(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState