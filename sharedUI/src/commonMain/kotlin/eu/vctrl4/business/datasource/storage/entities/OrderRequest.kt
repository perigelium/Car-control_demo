package eu.vctrl4.storage.entities

import eu.vctrl4.business.constants.*
import kotlinx.serialization.*

@Serializable
data class OrderListRequest( //
	var RightsOfUserId: String? = SessionVars.userSession.UserId, //
	var Limit: Int? = null, //
	var Offset: Int = 0
)
{
    var CarrierDepartmentId : String? = null //
    var ConsumerType : String? = null //
    var CreateDateFrom : String? = null //
    var CreateDateTo : String? = null //
    var CustomerCompanyId : String? = null //
    var CustomerDepartmentId : String? = null //
    var DateFrom : String? = null //
    var DateTo : String? = null //
    var Group : String? = null //
    var IsDeleted : Boolean = false //
    var NotEmptyGroup : Boolean? = null //
    var Number : String? = null //
    var NumberOrTicket : String? = null //
    var OrderType : Char? = null //
    var PriorityId : String? = null //
    var RouteStatus : String? = null //
    var SortOrder : String = "DESC" //
    var State : String? = null
    var TicketOrWayNumber : String? = null //
    var TicketState : String? = null //
    var UserId : String? = null //
    var VehicleNumber : String? = null //
    var WithDriver : Boolean? = null //

    var ShowHistory:Boolean? = null
}