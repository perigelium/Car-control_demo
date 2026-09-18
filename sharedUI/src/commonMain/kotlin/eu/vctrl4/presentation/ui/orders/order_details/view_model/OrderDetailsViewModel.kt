package eu.vctrl4.presentation.ui.orders.order_details.view_model


import androidx.lifecycle.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.CANCEL_REASON_CUSTOMER_ID
import eu.vctrl4.business.constants.Constants.LOCATION_TYPES_MAP
import eu.vctrl4.business.constants.Constants.NATURES_OF_WORK
import eu.vctrl4.business.constants.Constants.ORDER_PRIORITIES
import eu.vctrl4.business.constants.Constants.ORDER_TYPES_MAP
import eu.vctrl4.business.constants.SessionVars.userSession
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogMsg
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.requests.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.presentation.utils.OrderUtils.isOrderCancellable
import eu.vctrl4.ui.orders.order_details.view_model.*
import kotlinx.coroutines.*

class OrderDetailsViewModel(
    private val orderDetailsUseCase: OrderDetailsUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,

    private val requestVehicleTypesUseCase: RequestVehicleTypesUseCase,
    private val requestVehicleOptionsUseCase: RequestVehicleOptionsUseCase,
    private val requestVehicleCapacityClassesUseCase: RequestVehicleCapacityClassesUseCase,

    private val saveVehicleTypesToRoomUseCase: SaveVehicleTypesToRoomUseCase,
    private val saveVehicleOptionsToRoomUseCase: SaveVehicleOptionsToRoomUseCase,
    private val saveVehicleCapacityClassesToRoomUseCase: SaveVehicleCapacityClassesToRoomUseCase,

    private val getVehicleTypesFromRoomUseCase: GetVehicleTypesFromRoomUseCase,
    private val getVehicleOptionsFromRoomUseCase: GetVehicleOptionsFromRoomUseCase,
    private val getVehicleCapacityClassesFromRoomUseCase: GetVehicleCapacityClassesFromRoomUseCase
) : BaseViewModel<OrderDetailsEvent, OrderDetailsViewState, OrderDetailsAction>()
{
    override fun setInitialState() = OrderDetailsViewState()

    override fun onTriggerEvent(event: OrderDetailsEvent)
    {
        when (event)
        {
            is OrderDetailsEvent.OnUpdateNetworkState ->
            {
                if (event.networkState == NetworkState.Failed) display { UIComponent.Toast(Res.string.error_check_internet_connection.asState) }
            }

            is OrderDetailsEvent.CancelOrder ->
            {
                if (!isOrderCancellable(state.value.orderDetails))
                {
                    display({ UIComponent.Toast(Res.string.you_can_only_cancel_an_application_with_the_status_created.asState) })
                } else
                {
                    cancelOrder(event.strComment)
                }
            }

            is OrderDetailsEvent.RequestOrder ->
            {
                requestOrder(event.orderId)
            }
        }
    }

    fun requestOrder(orderId: String)
    {
        executeUseCase(orderDetailsUseCase.execute(params = orderId), onSuccess = { orderDetails ->
            if (orderDetails != null)
            {
                setState { copy(orderDetails = orderDetails) }

                prepareSupplierData()

                //getVehicleTypesLocal(null)
                //getVehicleOptionsLocal()
                //getVehicleCapacityClassesLocal()
            } else
            {
                display { DialogMsg(JAlertResponse(Res.string.error_load_order_details.asState)) }
            }
        }, onLoading = {
            setState { copy(progressBarState = it) }
        }, onNetworkStatus = {
            setEvent(OrderDetailsEvent.OnUpdateNetworkState(it))
        })
    }

    fun getVehicleTypesLocal(vehicleTypeId: String?)
    {
        executeUseCaseLocal(
            getVehicleTypesFromRoomUseCase.execute(params = vehicleTypeId, isFlow = false), onSuccess = { vehicleTypes ->

                if (vehicleTypes?.isNotEmpty() == true)
                {
                    if (vehicleTypeId == null)
                    {
                        setState { copy(vehicleTypes = vehicleTypes) }
                    }
                } else
                {
                    requestAndSaveVehicleTypes()
                }
            },

            onLoading = { setState { copy(progressBarState = it) } })
    }

    private fun requestAndSaveVehicleTypes()
    {
        executeUseCase(requestVehicleTypesUseCase.execute(Unit), onSuccess = { vehicleTypes ->
            if (vehicleTypes?.isNotEmpty() == true)
            { //val actualVehicleTypes: List<VehicleType> = vehicleTypes.filter { it.IsDeleted != true }
                val actualVehicleTypes = vehicleTypes.filterNot { it.IsDeleted == true }

                setState { copy(vehicleTypes = actualVehicleTypes) }

                executeUseCaseLocal(
                    saveVehicleTypesToRoomUseCase.execute(params = actualVehicleTypes, isFlow = false),
                    onSuccess = {},
                    onLoading = { setState { copy(progressBarState = it) } })
            } else
            {
                display { DialogMsg(JAlertResponse(Res.string.error_load_vehicle_types.asState)) }
            }
        }, onLoading = {
            setState { copy(progressBarState = it) }
        }, onNetworkStatus = {
            setEvent(OrderDetailsEvent.OnUpdateNetworkState(it))
        })
    }

    /*    fun observeOrderTypeVehicleOptionsLocalOnce(
            vehicleSubtypeId: String, orderVehicleOptionValueIds: List<String>
        )
        {
            val ldVTypes: LiveData<List<VehicleType>>? =
                App.database!!.vehicleTypeDao()?.getVehicleTypeById(vehicleSubtypeId)

            ldVTypes?.observe(lifecycleOwner, Observer<List<VehicleType>> { vehicleTypes ->

                vehicleTypes.let {

                    if (vehicleTypes.isNotEmpty())
                    {
                        val vTypeWithNotNullOptionTypes = vehicleTypes.filter { it.OptionTypes != null }
                        val vTypeOptionTypes = vTypeWithNotNullOptionTypes.flatMap { it.OptionTypes!! }
                        val actualVTypeOptionTypes = vTypeOptionTypes.filter { it.IsDeleted == false }

                        if (actualVTypeOptionTypes.isNotEmpty())
                        {
                            observeVehicleOptionsLocalOnce(orderVehicleOptionValueIds, actualVTypeOptionTypes)
                        }
                    }
                }
            })
        }*/

    fun getVehicleOptionsLocal()
    {
        executeUseCaseLocal(
            getVehicleOptionsFromRoomUseCase.execute(params = null, isFlow = false), onSuccess = { allVOptions ->

                if (allVOptions?.isNotEmpty() == true)
                {
                    setState { copy(vTypeOptionTypes = allVOptions) }
                } else
                {
                    requestAndSaveVehicleOptions()
                }
            },

            onLoading = { setState { copy(progressBarState = it) } })
    }

    private fun requestAndSaveVehicleOptions()
    {
        executeUseCase(requestVehicleOptionsUseCase.execute(Unit), onSuccess = { vehicleOptions ->
            if (vehicleOptions?.isNotEmpty() == true)
            {
                val actualVehicleOptions = vehicleOptions.filterNot { it.IsDeleted == true }

                setState { copy(vTypeOptionTypes = actualVehicleOptions) }

                executeUseCaseLocal(
                    saveVehicleOptionsToRoomUseCase.execute(params = actualVehicleOptions, isFlow = false),
                    onSuccess = {},
                    onLoading = { setState { copy(progressBarState = it) } })
            } else
            {
                display { DialogMsg(JAlertResponse(Res.string.error_load_vehicle_options.asState)) }
            }
        }, onLoading = {
            setState { copy(progressBarState = it) }
        }, onNetworkStatus = {
            setEvent(OrderDetailsEvent.OnUpdateNetworkState(it))
        })
    }

    fun getVehicleCapacityClassesLocal()
    {
        executeUseCaseLocal(
            getVehicleCapacityClassesFromRoomUseCase.execute(params = null, isFlow = false), onSuccess = { capacityClasses ->

                if (capacityClasses?.isNotEmpty() == true)
                {
                    setState { copy(vehicleCapacityClasses = capacityClasses) }
                } else
                {
                    requestAndSaveVehicleCapacityClassees()
                }
            },

            onLoading = { setState { copy(progressBarState = it) } })
    }

    private fun requestAndSaveVehicleCapacityClassees()
    {
        executeUseCase(requestVehicleCapacityClassesUseCase.execute(Unit), onSuccess = { vehicleCapacityClasses ->
            if (vehicleCapacityClasses?.isNotEmpty() == true)
            {
                val actualVehicleCapacityClasses: List<VehicleCapacityClass> =
                    vehicleCapacityClasses.filterNot { it.IsDeleted == true }

                setState { copy(vehicleCapacityClasses = actualVehicleCapacityClasses) }

                executeUseCaseLocal(
                    saveVehicleCapacityClassesToRoomUseCase.execute(params = actualVehicleCapacityClasses, isFlow = false),
                    onSuccess = {},
                    onLoading = { setState { copy(progressBarState = it) } })
            } else
            {
                display { DialogMsg(JAlertResponse(Res.string.error_load_vehicle_load_classes.asState)) }
            }
        }, onLoading = {
            setState { copy(progressBarState = it) }
        }, onNetworkStatus = {
            setEvent(OrderDetailsEvent.OnUpdateNetworkState(it))
        })
    }

    fun cancelOrder(textComment: String?)
    {
        val orderDetails = state.value.orderDetails

        if (orderDetails.Id == null || userSession.UserId == null)
        {
            display { UIComponent.Toast(Res.string.order_id_required_impossible_to_cancel_the_order.asState) }
            return
        }

        orderDetails.Id?.apply {

            val cancelOrderRequest = CancelOrderRequest(
	            CANCEL_REASON_CUSTOMER_ID,
	            textComment,
	            this,
	            userSession.UserId!!
                                                       )

            executeCancelOrder(cancelOrderRequest)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun executeCancelOrder(cancelOrderRequest: CancelOrderRequest)
    {
        executeUseCase(cancelOrderUseCase.execute(params = cancelOrderRequest), onSuccess = { resp ->
            if (resp != null)
            {
                if (resp.code == 0)
                {
					if(Constants.IS_DEMO_MODE)
	                {
		                state.value.orderDetails.State = "CN" // cancelled state
		                viewModelScope.launch {
			                val strOrderDetails = AppJson.encodeToString(state.value.orderDetails)
			                setState { copy(orderDetails = AppJson.decodeFromString(strOrderDetails)) }
		                }
	                }
                    display { DialogMsg(JAlertResponse(Res.string.success_order_cancelled.asState)) }
                    setAction { OrderDetailsAction.Popup(true) }
                } else if (resp.msg?.isNotBlank() == true)
                {
                    display { DialogMsg(JAlertResponse(Res.string.error.asState, resp.msg!!)) }
                }
            } else
            {
                display { DialogMsg(JAlertResponse(Res.string.error.asState,Res.string.error_send_cancel_order.asState)) }
            }
        }, onLoading = {
            setState { copy(progressBarState = it) }
        }, onNetworkStatus = {
            setEvent(OrderDetailsEvent.OnUpdateNetworkState(it))
        })
    }

    fun prepareSupplierData()
    {
        state.value.orderDetails.apply {
            val idNames: MutableList<IdNameValueName> = ArrayList()

            idNames.add(IdNameValueName(Res.string.organization.asState, CarrierCompanyName))
            idNames.add(IdNameValueName(Res.string.organization_department.asState, CarrierDepartmentName))

            setState { copy(titleTexts = idNames) }
        }
    }

    fun prepareCustomerData()
    {
        state.value.orderDetails.apply {

            val idNames: MutableList<IdNameValueName> = ArrayList()

            idNames.add(IdNameValueName(Res.string.customer.asState, CustomerCompanyName))

            idNames.add(IdNameValueName(Res.string.customer_department.asState, CustomerDepartmentName))

            idNames.add(IdNameValueName(Res.string.contact_person.asState, ContactPerson))

            idNames.add(IdNameValueName(Res.string.contact_person_phone.asState, ContactPhone))

            Inform?.apply {
                if (this.isNotEmpty())
                {
                    val inform = IdNameValueName(Res.string.sms_info.asState, null)
                    inform.isChecked = this.get(0).Type!! == "SMS"
                    idNames.add(inform)
                }
            }

            setState { copy(titleTexts = idNames) }
        }
    }

    fun prepareSupplyData()
    {
        state.value.orderDetails.apply {

            val idNames: MutableList<IdNameValueName> = ArrayList()

            val rentStartDateUI = DateTimeUtils.reformatDateTime(
	            RentStartDate, DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT, DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
                                                            )
            idNames.add(IdNameValueName(Res.string.delivery_date.asState, rentStartDateUI))

            val rentEndDateUI = DateTimeUtils.reformatDateTime(
                RentEndDate, DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT, DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
            )
            idNames.add(IdNameValueName(Res.string.end_of_usage.asState, rentEndDateUI))

            val rentTimeUI = DateTimeUtils.getTimerAsStringFromMins(RentTime ?: 0)
            idNames.add(IdNameValueName(Res.string.usage_time.asState, rentTimeUI))

            if ("RHD".equals(userSession.ProjectCode))
            {
                val strPlanningDistance = if (Mileage != null) "${Mileage?.toString()} km." else Res.string.no_data.asState
                idNames.add(IdNameValueName(Res.string.planned_mileage.asState, strPlanningDistance))
            }

            setState { copy(titleTexts = idNames) }
        }
    }

    fun prepareAdditionalData()
    {
        state.value.orderDetails.apply {

            val idNames: MutableList<IdNameValueName> = ArrayList()

            val strPriority: String = ORDER_PRIORITIES.find { PriorityId.equals(it.first) }?.second ?: Res.string.no_data.asState
            idNames.add(IdNameValueName(Res.string.priority.asState, strPriority))

            idNames.add(IdNameValueName(Res.string.responsible_person.asState, CreateUserName ?: ""))
            idNames.add(IdNameValueName(Res.string.comment.asState, Comment ?: ""))

            setState { copy(titleTexts = idNames) }
        }
    }

    fun prepareTransportationData()
    {
        state.value.orderDetails.apply {

            val idNamesTop: MutableList<IdNameValueName> = ArrayList()
            val idNamesBottom: MutableList<IdNameValueName> = ArrayList()

            OrderType.apply {
                idNamesTop.add(IdNameValueName(Res.string.order_type.asState, ORDER_TYPES_MAP.get(this)))
            }

            if (!"RHD".equals(userSession.ProjectCode))
            {
                idNamesTop.add(IdNameValueName(Res.string.vehicle_type.asState, VehicleTypeName))
            } else
            {
                idNamesTop.add(IdNameValueName(Res.string.vehicle_classifier.asState, VehicleTypeName))

                if (!VehicleSubclassName.isNullOrBlank())
                {
                    idNamesTop.add(IdNameValueName(Res.string.vehicle_sub_classifier.asState, VehicleSubclassName))
                }

                if (!VehicleSubTypeName.isNullOrBlank())
                {
                    idNamesTop.add(IdNameValueName(Res.string.vehicle_type.asState, VehicleSubTypeName))
                }
            }

            if (EquipmentTime != null && EquipmentTime!! > 0)
            {
                idNamesTop.add(
                    IdNameValueName(
                        Res.string.equipment_operation_time.asState, DateTimeUtils.getTimerAsStringFromMins(EquipmentTime!!)
                    )
                )
            }

            if (!VehicleName.isNullOrBlank())
            {
                idNamesTop.add(IdNameValueName(Res.string.car.asState, VehicleName))
            }

            setState { copy(titleTextsTranspTop = idNamesTop) }

            if (VehicleSubTypeId != null && !Options.isNullOrEmpty())
            {
                val vehicleTypes = state.value.vehicleTypes
                val vTypeWithNotNullOptionTypes = vehicleTypes.filter { it.OptionTypes != null }
                val vTypeOptionTypes = vTypeWithNotNullOptionTypes.flatMap { it.OptionTypes!! }
                val actualVTypeOptionTypes = vTypeOptionTypes.filterNot { it.IsDeleted == true }

                if (actualVTypeOptionTypes.isNotEmpty())
                {
                    val allVOptions = state.value.vTypeOptionTypes
                    val vOptionsWithEnumOptions = allVOptions.filter { it.EnumOptions != null }

                    for (vTypeOptionType in actualVTypeOptionTypes) // all option types for certain vehicle type
                    {
                        val matchedVOption = vOptionsWithEnumOptions.find { it.Id == vTypeOptionType.Id }

                        matchedVOption?.apply {

                            val matchedEnumOptionValues: List<IdNameValueName>? =
                                matchedVOption.EnumOptions?.filter { it.Id in Options!! }

                            matchedEnumOptionValues?.apply {

                                for (optionValue in this) // enum option values of found vOption
                                {
                                    vTypeOptionType.valueName = optionValue.Name
                                }
                            }
                        }
                    }
                    val vTypeOptionTypesWithFoundOptions = actualVTypeOptionTypes.filter { it.valueName != null }
                    setState { copy(titleTextsTranspOptionTypes = vTypeOptionTypesWithFoundOptions) }
                }
            }

            WithDriver.apply {
                idNamesBottom.add(
                    IdNameValueName(
                        Res.string.service.asState, if (this) Res.string.with_driver.asState else Res.string.without_driver.asState
                    )
                )
            }

            idNamesBottom.add(IdNameValueName(Res.string.transportation_type.asState,  LOCATION_TYPES_MAP.get(LocationType)))

            val extParameters: Map<String, Any?>? = this.ExtParameters?.toMapStringAny()

            idNamesBottom.add(IdNameValueName(Res.string.route.asState, extParameters?.get("Route")?.toString() ?: ""))

			if (!NatureOfWorks.isNullOrEmpty())
            {
	            NatureOfWorks?.let { natures->
		            val natureOfWorkIds = natures.map { it.Id }
		            val listOfNames = NATURES_OF_WORK.filter { it.Id in natureOfWorkIds }.map { it.Name }
		            val strListOfNames = listOfNames.joinToString(", ")
		            strListOfNames.apply {
			            idNamesBottom.add(
				            IdNameValueName(
					            Res.string.nature_of_work_purpose_of_trip.asState,
					            this
				                           )
			                             )
		            }
	            }
            }

            if (extParameters?.get("IsLongDuration") as? Boolean == true)
            {
                val businessTrip = IdNameValueName(Res.string.business_trip.asState, null)
                businessTrip.isChecked = true
                idNamesBottom.add(businessTrip)
            }

            this.CapacityClassId?.apply {

                val vehicleCapacityClass = state.value.vehicleCapacityClasses.firstOrNull { it.Id == this }
                vehicleCapacityClass?.let {
                    idNamesBottom.add(idNamesBottom.size, IdNameValueName(Res.string.load_capacity_class.asState, it.Name))
                }
            }

            if (OrderType == 'P')
            {
                idNamesBottom.add(IdNameValueName(Res.string.passenger_count.asState, NumberOfPassengers?.toString()))
            } else if (OrderType == 'C')
            {
                idNamesBottom.add(
                    IdNameValueName(
                        Res.string.cargo_name.asState, extParameters?.get("ShippingName")?.toString() ?: ""
                    )
                )
                idNamesBottom.add(IdNameValueName(Res.string.cargo_weight.asState, CargoWeight?.toString()))
            }

            if (!TrailerTypeName.isNullOrBlank())
            {
                val trailerPresence = IdNameValueName(Res.string.trailer_availability.asState, null)
                trailerPresence.isChecked = true
                idNamesBottom.add(trailerPresence)

                idNamesBottom.add(IdNameValueName(Res.string.trailer_type.asState, TrailerTypeName))
            }

            if (OrderType == 'C')
            {
                val strCargoOversize = if (extParameters?.get("CargoOversize") as? Boolean == true) Res.string.Yes.asState else Res.string.no.asState
                idNamesBottom.add(IdNameValueName(Res.string.oversized_cargo.asState, strCargoOversize))
            }

            if (OrderType == 'S')
            {
                if (extParameters?.get("AreaOfWork")?.toString()?.isNotBlank() == true)
                {
                    idNamesBottom.add(IdNameValueName(Res.string.work_sector.asState, extParameters.get("AreaOfWork")?.toString()))
                }

                if (extParameters?.get("Slingers")?.toString()?.isNotBlank() == true)
                {
                    idNamesBottom.add(IdNameValueName(Res.string.slingers.asState, extParameters.get("Slingers")?.toString()))
                }

                if (extParameters?.get("Responsible")?.toString()?.isNotBlank() == true)
                {
                    idNamesBottom.add(
                        IdNameValueName(
                            Res.string.crane_safety_responsible_person.asState, extParameters.get("Responsible")?.toString()
                        )
                    )
                }

                extParameters?.get("WorkNearPowerLines")?.apply {
                    val trailerPresence = IdNameValueName(Res.string.work_near_power_lines.asState, null)
                    trailerPresence.isChecked = (extParameters.get("WorkNearPowerLines") as? Boolean) == true
                    idNamesBottom.add(trailerPresence)
                }
            }

            idNamesBottom.add(IdNameValueName(Res.string.work_location_address.asState, RentAddress))

            if (AddressPoints?.size != null && AddressPoints?.size!! > 1)
            {
                val rentEndAddress = AddressPoints!!.get(AddressPoints!!.size - 1).Address
                idNamesBottom.add(IdNameValueName(Res.string.destination_address.asState, rentEndAddress))
            }

            setState { copy(titleTextsTranspBottom = idNamesBottom) }
        }
    }
}