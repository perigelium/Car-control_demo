package eu.vctrl4.presentation.ui.orders.order_list.filter.view_model


import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import eu.vctrl4.ui.orders.order_list.filter.view_model.*


class OrdersFilterViewModel(val getCompaniesFromRoomUseCase: GetCompaniesFromRoomUseCase) :
    BaseViewModel<OrdersFilterViewEvent, OrdersFilterViewState, OrdersFilterAction>()
{
    override fun setInitialState() = OrdersFilterViewState()

    init
    {
        setInitialState()
    }

    override fun onTriggerEvent(event: OrdersFilterViewEvent)
    {
        when (event)
        {

            is OrdersFilterViewEvent.OnFilterSubmitted ->
            {
                setAction { OrdersFilterAction.Popup }
            }

            is OrdersFilterViewEvent.OnFilterInvoked ->
            {
                prepareCompaniesLocal()
            }

            is OrdersFilterViewEvent.OnShowMessage -> display { Toast(event.message) }
        }
    }

    fun prepareCompaniesLocal()
    {
        executeUseCaseLocal(
            getCompaniesFromRoomUseCase.execute(params = Unit, isFlow = false), onSuccess = { allCompanies ->

                val supplierCompanies: MutableList<Company> =
                    allCompanies?.filter { it.IsSupplier == true } as MutableList<Company>
                val suppDepartments = initSuppliersList(supplierCompanies)

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

                setState { copy(custCompanies = custCompaniesWithZeroItem, suppDepartments = suppDepartments) }
            },

            onLoading = { setState { copy(progressBarState = it) } })
    }

    /*    private suspend fun asyncCompanies(): List<Company>?
        {
            val coroutineName = object {}.javaClass.enclosingMethod?.name ?: ""

            val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName(coroutineName))
            val deferred = scope.async {
                return@async App.Companion.database.companyDao()?.getAllCompaniesAsync()
            }
            return deferred.await()
        }*/

    private fun initSuppliersList(companies: List<Company>): List<Department>
    {
        val suppDepartments: MutableList<Department> = ArrayList()

        for (company in companies)
        {
            company.Departments?.apply {
                val departments = this.filter { it.HasChildren == false }
                val departmentsNotExpired: List<Department> = departments.filter { it.ExpirationDate == null }
                suppDepartments.addAll(departmentsNotExpired)
            }
        }

        if (suppDepartments.isNotEmpty())
        {
            val emptySupplier = Department()
            emptySupplier.Name = Res.string.all.asState
            suppDepartments.add(0, emptySupplier)
        }

        for (suppDepartment in suppDepartments)
        {
            suppDepartment.IsSupplier = true
        }
        return suppDepartments
    }
}
