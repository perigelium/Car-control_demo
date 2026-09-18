package eu.vctrl4.presentation.ui.tickets.ticket_list.filter.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*


data class TicketsFilterViewState(
	val custCompanies: List<Company> = listOf(),
	val progressBarState: ProgressBarState = ProgressBarState.Idle,
) : ViewState