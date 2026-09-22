package eu.vctrl4.business.datasource.storage.entities

import eu.vctrl4.business.datasource.network.main.requests.DoneRequest
import kotlinx.serialization.*

@Serializable
class Order
{
    var OrderId: String? = null  
    var ExcludeOrderId:String? = null
    var OrderType: Char = 'C'  
    var Number: String? = null  
    var CreateDate: String? = null  
    var RentStartDate: String? = null  
    var RentAddress: String? = null  
    var State: String? = null  
    var StateName: String? = null  
    var CustomerCompanyName: String? = null  
    var CustomerDepartmentName: String? = null  
    var CarrierDepartmentName: String? = null  
    var VehicleName: String? = null  
    var RentTime: Int? = null  
    var rentTimeDaily: Int? = null
    var rentWeekDays: List<Boolean> = arrayListOf(true, true, true, true, true, true, true)
    var EquipmentTime: Int? = null  
    var Mileage: Int? = null  
    var Priority: Char = 'L'  
    var PriorityName: String? = null  
    var CreateUserName: String? = null  
    var CancelReasonName: String? = null  
    var ConsumerType: String? = null  
    var Rating: Float? = null  
    var Comment: String? = null  

    var Ticket: Ticket? = null

    var AddressPoints: List<AddressPoint>? = null
    var CapacityClassId: String? = null
    var CargoWeight: Float? = null
    var CarrierCompanyId: String? = null
    var CarrierCompanyName: String? = null
    var CarrierDepartmentId: String? = null
    var ContactPerson: String? = null
    var ContactPhone: String? = null
    var CreateUserId: String? = null
    var CreateUserPhone: String? = null
    var CustomerCompanyId: String? = null
    var CustomerDepartmentId: String? = null
    var CustomerRating: Float? = null
    var CustomerTypeGroupId: String? = null
    var Date: String? = null
    var DeclineReason: String? = null
    var DeclineReasonId: String? = null
    var ExtParameters: ExtParameters? = null
    var FiasId: String? = null
    @Contextual
    var GeoZones: Any? = null
    var Group: String? = null
    var History:List<OrderHistoryItem>? = null
    var Id: String? = null // For requests
    var Inform: List<Inform>? = null
    var IsDeleted: Boolean? = null
    var IsProtest: Boolean? = null
    var Latitude: Double? = null
    var LocationType: String? = null
    var LocationTypeName: String? = null
    var Longitude: Double? = null
    var MetaOrderId: String? = null
    var ModelId: String? = null
    var ModelName: String? = null
    var ModifyConsumerType: String? = null
    var ModifyDate: String? = null
    var NatureOfWorks: List<NatureOfWork>? = null // Read only !
    var NumberOfPassengers: Int? = null
    var Options: List<String>? = null
    var PlannedAmount: Float? = null
    var PriorityId: String? = null //Constants.PRIORITY_IDS.get('L')
    var RentEndDate: String? = null
    var RentType: Char = 'T' // Time is default
    val Route: String? = null // read only !

    var TrailerTypeId: String? = null
    var TrailerTypeName: String? = null
    var UserId: String? = null
    var UserName: String? = null
    var VehicleId: String? = null
    var VehicleSubTypeId: String? = null //if ("RHD".equals(SessionVars.userSession.ProjectCode)) null else null
    var VehicleSubTypeName: String? = null
    var VehicleSubclassId: String? = null //if ("RHD".equals(SessionVars.userSession.ProjectCode)) null else null
    var VehicleSubclassName: String? = null
    var VehicleTypeGroupId: String? = null
    var VehicleTypeId: String? = null
    var VehicleTypeName: String? = null
    var WithDriver: Boolean = true // true is default

    var DoneRequest: DoneRequest? = null
}