package eu.vctrl4.business.datasource.storage.entities

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.TICKET_STATES_MAP
import eu.vctrl4.common.*
import eu.vctrl4.presentation.utils.*

class TitleTextAttrLists(val order:Order)
{
    val orderTitleTextAttrs: MutableList<TitleTextAttrs> by lazy {
        mutableListOf(
	        TitleTextAttrs(
		        title = Res.string.creation_date.asState, strText = DateTimeUtils.reformatDateTime(
			        order.CreateDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_LONG,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                                                          ) ?: ""
	                      ), TitleTextAttrs(
		        title = Res.string.vehicle_delivery_date.asState, strText = DateTimeUtils.reformatDateTime(
			        order.RentStartDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                                          ) ?: ""
	                                       ), TitleTextAttrs(
		        title = Res.string.customer.asState,
		        strText = order.CustomerCompanyName?.substringBefore(
			        '(',
			        order.CustomerCompanyName!!
		                                                            ) ?: Res.string.no_data.asState
	                                                        )
	        , TitleTextAttrs(
		        title = Res.string.customer_department.asState,
		        strText = order.CustomerDepartmentName?.substringBefore(
			        '(',
			        order.CustomerDepartmentName!!
		                                                               ) ?: Res.string.no_data.asState
	                        )
        )
    }

    val ticketTitleTextAttrs: MutableList<TitleTextAttrs> by lazy {

        val strCustomerCompany = order.CustomerCompanyName?.substringBefore(' ')?:""
        val strCustomer = "$strCustomerCompany - ${order.CustomerDepartmentName?:""}"

        mutableListOf(
	        TitleTextAttrs(
		        title = Res.string.customer.asState, strText = strCustomer, titleTextWidthBetween = 125
	                      ), TitleTextAttrs(
		        title = Res.string.arrival.asState,
		        strText = DateTimeUtils.reformatDateTime(
			        order.Ticket?.StartDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                ) ?: "",
		        titleTextWidthBetween = 125
	                                       ), TitleTextAttrs(
		        title = Res.string.departure.asState,
		        strText = DateTimeUtils.reformatDateTime(
			        order.Ticket?.EndDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                ) ?: "",
		        titleTextWidthBetween = 125
	                                                        )
        )
    }

    val ticketTitleTwoTextsAttrs: MutableList<TitleTwoTextsAttrs> by lazy {

        val strRentTimeOrder = order.RentTime?.toLong()?.let { DateTimeUtils.getHoursMinsStringFromMinutes(it) }
        val strRentTimeTicket = order.Ticket?.ConfirmedTime?.toLong()?.let { DateTimeUtils.getHoursMinsStringFromMinutes(it) }

        var strEquipTimeOrder = order.EquipmentTime?.toLong()?.let { DateTimeUtils.getHoursMinsStringFromMinutes(it) }
        if(strEquipTimeOrder == "00:00" ) strEquipTimeOrder = ""
        var strEquipTimeTicket = order.Ticket?.EquipmentTime?.toLong()?.let { DateTimeUtils.getHoursMinsStringFromMinutes(it) }
        if(strEquipTimeTicket == "00:00" ) strEquipTimeTicket = ""

        mutableListOf(
	        TitleTwoTextsAttrs(
		        title = Res.string.time.asState,
		        strText1 = strRentTimeOrder,
		        strText2 = strRentTimeTicket,
		        titleTextWidthBetween = 100,
		        text1Text2WidthBetween = 90
	                          ), TitleTwoTextsAttrs(
		        title = "Equipment\noperating hours",
		        strText1 = strEquipTimeOrder,
		        strText2 = strEquipTimeTicket,
		        titleTextWidthBetween = 100,
		        text1Text2WidthBetween = 90
	                                               ), TitleTwoTextsAttrs(
		        title = Res.string.mileage.asState,
		        strText1 = order.Mileage?.toString() ?: "-",
		        strText2 = order.Ticket?.Mileage?.toString() ?: "-",
		        titleTextWidthBetween = 100,
		        text1Text2WidthBetween = 90
	                                                                    )
        )
    }

    val ticketConfirmTitleTextAttrs: MutableList<TitleTextAttrs> by lazy {
        mutableListOf(
	        TitleTextAttrs(
		        title = Res.string.ticket_status.asState,
		        strText = TICKET_STATES_MAP[order.Ticket?.State]?:"",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.waydoc_number.asState,
		        strText = order.Ticket?.WayDocNumber ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.customer.asState,
		        strText = order.CustomerCompanyName ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.customer_department.asState,
		        strText = order.CustomerDepartmentName ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.license_plate.asState,
		        strText = order.Ticket?.VehicleNumber ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.driver.asState,
		        strText = order.Ticket?.DriverName ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.arrival.asState,
		        strText = DateTimeUtils.reformatDateTime(
			        order.Ticket?.StartDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                ) ?: "",
		        titleTextWidthBetween = 125
	                      ),
	        TitleTextAttrs(
		        title = Res.string.departure.asState,
		        strText = DateTimeUtils.reformatDateTime(
			        order.Ticket?.EndDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                ) ?: "",
		        titleTextWidthBetween = 125
	                      )
        )
    }

    val subcontractTitleTextAttrs: MutableList<TitleTextAttrs> by lazy {
        mutableListOf(
	        TitleTextAttrs(
		        title = Res.string.responsible_person.asState,
		        strText = order.DoneRequest?.RequestCreatorName ?: "",
		        titleTextWidthBetween = 125
	                      ), TitleTextAttrs(
		        title = Res.string.order_sent.asState,
		        strText = DateTimeUtils.reformatDateTime(
			        order.DoneRequest?.RequestDate,
			        DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
			        DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                ) ?: "",
		        titleTextWidthBetween = 125
	                                       ), TitleTextAttrs(
		        title = Res.string.comment.asState,
		        strText = order.DoneRequest?.RequestComment ?: "",
		        titleTextWidthBetween = 125
	                                                        )
        )
    }
}