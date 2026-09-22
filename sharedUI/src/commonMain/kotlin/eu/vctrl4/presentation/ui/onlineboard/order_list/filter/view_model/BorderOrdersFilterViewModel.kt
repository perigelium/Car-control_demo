package eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model


import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*


class BoardOrdersFilterViewModel(private val getCompaniesFromRoomUseCase: GetCompaniesFromRoomUseCase) :
	BaseViewModel<BoardOrdersFilterViewEvent, BoardOrdersFilterViewState, BoardOrdersFilterAction>() {
	override fun setInitialState() = BoardOrdersFilterViewState()

	init {
		setInitialState()
	}

	override fun onTriggerEvent(event: BoardOrdersFilterViewEvent) {
		when (event) {

			is BoardOrdersFilterViewEvent.OnFilterSubmitted -> {
				setAction { BoardOrdersFilterAction.Popup }
			}

			is BoardOrdersFilterViewEvent.OnFilterInvoked -> {
				requestCompaniesLocal()
			}
		}
	}

	private fun getCompaniesFromRoom() {
		executeUseCaseLocal(
			getCompaniesFromRoomUseCase.execute(params = Unit, isFlow = false),
			onSuccess = { setState { copy(companies = it ?: emptyList()) } },
			onLoading = { setState { copy(progressBarState = it) } })
	}


	fun requestCompaniesLocal() {

		getCompaniesFromRoom()

		val customerCompanies: MutableList<Company> =
			state.value.companies.filter { it.IsCustomer == true } as MutableList<Company>

		val emptyCompany = Company()
		emptyCompany.Name = Res.string.all.asState
		val companiesWithZeroItem = customerCompanies.toMutableList()
		companiesWithZeroItem.add(0, emptyCompany)

		setState { copy(companies = companiesWithZeroItem) }
	}
}
