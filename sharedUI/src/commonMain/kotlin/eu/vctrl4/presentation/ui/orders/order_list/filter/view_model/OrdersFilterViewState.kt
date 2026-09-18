package eu.vctrl4.presentation.ui.orders.order_list.filter.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*


data class OrdersFilterViewState(
	val suppDepartments:List<Department> = listOf(),
	val custCompanies:List<Company> = listOf(),
	val custDepartments:List<Department> = listOf(),
	val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState