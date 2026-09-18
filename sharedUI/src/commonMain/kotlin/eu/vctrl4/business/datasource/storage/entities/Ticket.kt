package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.*


@Serializable
class Ticket
{
    var State : Char? = null  
    //var StateName : String? = null
    var Number : String? = null  
    var StartDate : String? = null  
    var EndDate : String? = null  
    var ConfirmedTime : Int? = null  
    var EquipmentTime : Int? = null  
    var Mileage : Int? = null  
    var ConfirmedAmount : Float? = null  
    var VehicleNumber : String? = null  
    var DriverName : String? = null  
    var DriverPhone : String? = null //	string
    var MobileWaybill : Boolean? = null //	string
    var WayDocNumber : String? = null  

    var Comment: String? = null //":
}