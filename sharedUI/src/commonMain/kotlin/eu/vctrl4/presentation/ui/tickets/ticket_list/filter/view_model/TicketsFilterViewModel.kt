package eu.vctrl4.presentation.ui.tickets.ticket_list.filter.view_model


import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*


class TicketsFilterViewModel(val getCompaniesFromRoomUseCase: GetCompaniesFromRoomUseCase) :
    BaseViewModel<TicketsFilterViewEvent, TicketsFilterViewState, TicketsFilterAction>()
{
    override fun setInitialState() = TicketsFilterViewState()

    init
    {
        setInitialState()
    }

    override fun onTriggerEvent(event: TicketsFilterViewEvent)
    {
        when (event) {

            is TicketsFilterViewEvent.OnFilterSubmitted -> {
                setAction { TicketsFilterAction.Popup }
            }

            is TicketsFilterViewEvent.OnFilterInvoked ->
            {
	            prepareCompaniesLocal()
            }

            is TicketsFilterViewEvent.OnShowMessage -> display { Toast(event.message) }
        }
    }

	fun prepareCompaniesLocal()
	{
		executeUseCaseLocal(
			getCompaniesFromRoomUseCase.execute(params = Unit, isFlow = false), onSuccess = { allCompanies ->

				val customerCompanies: MutableList<Company> =
					allCompanies?.filter { it.IsCustomer == true } as MutableList<Company>

				val emptyCompany = Company()
				emptyCompany.Name = Res.string.all.asState

				val custCompaniesWithZeroItem = customerCompanies.toMutableList()
				custCompaniesWithZeroItem.add(0, emptyCompany)

				customerCompanies.forEach { company ->

					val emptyCustomer = Department()
					emptyCustomer.Name = Res.string.all.asState

					val mutableDepartments = company.Departments?.toMutableList()
					mutableDepartments?.add(0, emptyCustomer)
					company.Departments = mutableDepartments
				}

				setState { copy(custCompanies = custCompaniesWithZeroItem) }
			},

			onLoading = { setState { copy(progressBarState = it) } })
	}
}
