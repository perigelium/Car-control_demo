package eu.vctrl4.business.constants

import androidx.compose.ui.graphics.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.base.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

object Constants {
	const val IS_DEMO_MODE = true
	const val STATUS_OK = 0
	const val DB_VERSION = 208

	val ORDER_TYPES_MAP: Map<Char?, String>
		get() = mutableMapOf<Char?, String>().apply {

		put(null, Res.string.all.asState)
		put('C', Res.string.cargo_transportation.asState)
		put('P', Res.string.passenger_transportation.asState)
		put('S', Res.string.special_equipment.asState)
	}

	val ORDER_TYPES: List<IdNameSimple> get() = listOf(

		IdNameSimple(null, Res.string.all.asState),
		IdNameSimple("C", Res.string.cargo_transportation.asState),
		IdNameSimple("P", Res.string.passenger_transportation.asState),
		IdNameSimple("S", Res.string.special_equipment.asState),
	                                            )

	val ORDER_PRIORITIES_ID_NAMES: List<IdNameSimple> get() = mutableListOf<IdNameSimple>().apply {

		add(IdNameSimple(null, Res.string.all.asState))
		add(IdNameSimple("7ff1bab7-6e91-44b4-8493-2c43cdca5d23", Res.string.priority_low.asState))
		add(IdNameSimple("d32e8cca-7ba3-4bd9-a248-013b39c5127b", Res.string.priority_medium.asState))

		add(IdNameSimple("b9f4442f-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_accident.asState))
		add(IdNameSimple("c86fd515-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_window_work.asState))
		add(IdNameSimple("c2515b15-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_emergency.asState))
		add(IdNameSimple("5eb7aabf-93e6-47c1-949a-1949f76264ca", Res.string.priority_high_other.asState))
	}

	val ORDER_PRIORITIES: List<Pair<String, String>> get() = mutableListOf<Pair<String, String>>().apply {

		add(Pair("7ff1bab7-6e91-44b4-8493-2c43cdca5d23", Res.string.priority_low.asState))
		add(Pair("d32e8cca-7ba3-4bd9-a248-013b39c5127b", Res.string.priority_medium.asState))

		add(Pair("b9f4442f-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_accident.asState))
		add(Pair("c86fd515-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_window_work.asState))
		add(Pair("c2515b15-c861-11e6-80d5-10604ba895d8", Res.string.priority_high_emergency.asState))
		add(Pair("5eb7aabf-93e6-47c1-949a-1949f76264ca", Res.string.priority_high_other.asState))
	}

	val ORDER_STATES_MAP: Map<String?, String> get() = mutableMapOf<String?, String>().apply {

		put(null, Res.string.all.asState)
		put("NW", Res.string.created.asState)
		put("PL", Res.string.accepted_order.asState)
		put("AP", Res.string.under_approval.asState)
		put("NC", Res.string.not_approved.asState)
		put("RD", Res.string.completed.asState)
		put("CN", Res.string.cancelled.asState)

	}

	val ORDER_STATES: List<IdNameSimple> get() = mutableListOf<IdNameSimple>().apply {
		add(IdNameSimple(null, Res.string.all.asState))
		add(IdNameSimple("NW", Res.string.created.asState))
		add(IdNameSimple("PL", Res.string.accepted_order.asState))
		add(IdNameSimple("AP", Res.string.under_approval.asState))
		add(IdNameSimple("NC", Res.string.not_approved.asState))
		add(IdNameSimple("RD", Res.string.completed.asState))
		add(IdNameSimple("CN", Res.string.cancelled.asState))
	}

	val TICKET_STATES_MAP: Map<Char?, String> get() = mutableMapOf<Char?, String>().apply {

		put(null, Res.string.all.asState)
		put('N', Res.string.pending_confirmation.asState)
		put('R', Res.string.confirmed.asState)
		put('C', Res.string.confirmation_denied.asState)
		put('S', Res.string.signed.asState)

	}

	val TICKET_STATES: List<IdNameSimple> get() = mutableListOf<IdNameSimple>().apply {

		add(IdNameSimple(null, Res.string.all.asState))
		add(IdNameSimple("N", Res.string.pending_confirmation.asState))
		add(IdNameSimple("R", Res.string.confirmed.asState))
		add(IdNameSimple("C", Res.string.confirmation_denied.asState))
		add(IdNameSimple("S", Res.string.signed.asState))

	}

	val LOCATION_TYPES_MAP: Map<Char?, String> get() = mutableMapOf<Char?, String>().apply {

		put(null, Res.string.all.asState)
		put('U', Res.string.urban.asState)
		put('S', Res.string.suburban.asState)
		put('C', Res.string.intercity.asState)
		put('I', Res.string.international.asState)
	}

	val PRIORITY_COLORS_INT: Map<Char, Color> get() = mutableMapOf<Char, Color>().apply {

		put('L', Colors.cl_69BE28)
		put('M', Colors.cl_FFBC0F)
		put('H', Colors.cl_FB5152)

	}

	val PRIORITY_IDS: Map<Char, String> = mutableMapOf<Char, String>().apply {

		put('H', "5eb7aabf-93e6-47c1-949a-1949f76264ca")
		put('L', "7ff1bab7-6e91-44b4-8493-2c43cdca5d23")
		put('M', "d32e8cca-7ba3-4bd9-a248-013b39c5127b")

	}

	val ORDER_TYPES_ICONS: Map<Char, DrawableResource> =
		mutableMapOf<Char, DrawableResource>().apply {

			put('P', Res.drawable.ic_bus)
			put('C', Res.drawable.ic_truck)
			put('S', Res.drawable.ic_tracktor)

		}

	val CONSUMER_TYPES: List<IdNameSimple> get() = mutableListOf<IdNameSimple>().apply {

		add(IdNameSimple(null, Res.string.all.asState))
		add(IdNameSimple("Web", "Web 2.0"))
		add(IdNameSimple("WEB2", "Web 1.0"))
		add(IdNameSimple("CS", Res.string.accounting_system.asState))
		add(IdNameSimple("Mob", Res.string.mobile_app.asState))
		add(IdNameSimple("HOT", Res.string.hotline.asState))  
	}

	val NATURES_OF_WORK: List<IdNameSimple> = mutableListOf<IdNameSimple>().apply {
		add(IdNameSimple("fe607074-62a0-11e5-80bc-10604ba895d8", "Passenger Transportation"))
		add(IdNameSimple("76b62454-62a3-11e5-80bc-10604ba895d8", "Special Equipment Operations"))
		add(IdNameSimple("796cc448-62af-11e5-80bc-10604ba895d8", "Special / Utility Operations"))
		add(
			IdNameSimple(
				"20838422-62b2-11e5-80bc-10604ba895d8",
				"Mixed Cargo and Passenger Transportation"
			            )
		   )
		add(IdNameSimple("e0459c28-62bd-11e5-80bc-10604ba895d8", "Employee Shuttle Services"))
		add(IdNameSimple("ed650b54-62bd-11e5-80bc-10604ba895d8", "On-Call / Standby Duty"))
		add(IdNameSimple("fd201308-62bd-11e5-80bc-10604ba895d8", "Executive Transportation"))
		add(IdNameSimple("55c79bb1-ed95-11e8-80ef-10604ba895d8", "Emergency Response Operations"))
		add(IdNameSimple("73e1c407-ed95-11e8-80ef-10604ba895d8", "Lifting Equipment Operations"))
		add(IdNameSimple("73e1c408-ed95-11e8-80ef-10604ba895d8", "Cross-Border Operations"))
		add(IdNameSimple("80a1e975-ed95-11e8-80ef-10604ba895d8", "Road and Route Clearance"))
		add(IdNameSimple("90becdb4-ed95-11e8-80ef-10604ba895d8", "Right-of-Way Maintenance"))
		add(IdNameSimple("807e5aac-6350-11e5-80bc-10604ba895d8", "Freight Transportation"))
		add(
			IdNameSimple(
				"5c8d0413-ed95-11e8-80ef-10604ba895d8",
				"Specialized Cargo Transportation"
			            )
		   )
		add(IdNameSimple("b0c59099-f05b-11eb-a107-005056abd2b4", "Hazmat Transportation"))
	}

	val CONSUMER_TYPES_ICONS: Map<String, DrawableResource> =
		mutableMapOf<String, DrawableResource>().apply {

			put("Web", Res.drawable.ic_globus)
			put("CS", Res.drawable.ic_globus)
			put("Mob", Res.drawable.ic_mobile)

		}

		val BOTTOM_APP_BAR_NAV_ITEMS: List<NavigationItem> get() = arrayListOf(
			NavigationItem(
				title = Res.string.performer_data.asState, name = "nav_supplier", unselectedIconRes = Res.drawable.ic_supplier_blue
						  ), NavigationItem(
				title = Res.string.customer_data.asState, name = "nav_customer", unselectedIconRes = Res.drawable.ic_customer_blue
										   ), NavigationItem(
				title = Res.string.delivery_data.asState, name = "nav_supply", unselectedIconRes = Res.drawable.ic_calendar_blue
															), NavigationItem(
				title = Res.string.transportation_data.asState, name = "nav_transportation", unselectedIconRes = Res.drawable.ic_car_blue
																			 ), NavigationItem(
				title = Res.string.additional_information.asState, name = "nav_additional", unselectedIconRes = Res.drawable.ic_additional_blue
																							  )
			, NavigationItem(
				title = "Spacer", name = "nav_stub", unselectedIconRes = Res.drawable.transparent24px
							)
																			 )

	// T[ime], M[ileage], F[act],
	// S[um = Time + Mileage] (string)
	const val TIME = "T"
	const val MILEAGE = "M"
	const val SUM = "S"
	const val FACT = "F"
	const val API_KEY = "_"
	const val BACK_END_SUBSCRIBER = "android"

	// Filters counter flags
	const val TIME_PERIOD_TYPES = 1
	const val TIME_PERIOD_RANGE = 2
	const val VEHICLE_REG_NUMBER = 4
	const val SUPPLIER_DEPARTMENT = 8
	const val CUSTOMER_COMPANY = 16
	const val CUSTOMER_DEPARTMENT = 32
	const val ORDER_TYPE = 64
	const val ORDER_STATE = 128
	const val CONSUMER_TYPE = 256
	const val ORDER_PRIORITY = 512
	const val ORDERS_BY_CURRENT_USER = 1024

	enum class ConfirmCancel {
		CONFIRM, CANCEL
	}

	val BY_ORDER_REGISTRATION get() =
		IdNameValueName("BY_ORDER_REGISTRATION", Res.string.by_order_registration_date.asState)
	val BY_RENT_TIME_START get() = IdNameValueName("BY_RENT_TIME_START", Res.string.by_vehicle_delivery_date.asState)

	/*	val CANCELS_COLORS = intArrayOf(
			rgb("#e02020"),
			rgb("#005fa7"),
			rgb("#1d9a8b"),
			rgb("#32c5ff"),
			rgb("#6dd400"),
			rgb("#f7b500"),
			rgb("#f1a6c0"),
			rgb("#eb719c")
									   )*/

	//val LIMITS_COLORS = intArrayOf(rgb("#005fa7"), rgb("#e0ebef"), rgb("#f40000"), rgb("#005fa7"))

	val CANCEL_REASONS_PERFORMER get() = arrayOf(
		Res.string.vehicle_repair.asState,Res.string.reason_missing_required_driver.asState,Res.string.reason_missing_documents_or_cards.asState,Res.string.order_status_created.asState
	                                      )

	val CANCEL_REASONS_CUSTOMER get() = arrayOf(
		Res.string.vehicle_capital_repair.asState, Res.string.reason_missing_required_vehicle_type.asState, Res.string.reason_untimely_customer_order.asState, Res.string.reason_missing_insurance.asState, Res.string.reason_missing_tachograph.asState, Res.string.reason_no_transport_services_limit.asState, Res.string.reason_customer_refusal.asState, Res.string.reason_missing_hazardous_waste_permits.asState
	                                     )

	val CANCEL_REASON_CUSTOMER_ID = "5561b852-384d-11e5-989b-00155d630038"

	const val HOUR_IN_MILLIS = 1000 * 60 * 60
	const val HOURS_24_MILLIS = HOUR_IN_MILLIS * 24
	const val PORTION_OF_ORDERS_PER_PAGE = 10
}