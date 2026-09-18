package eu.vctrl4.storage.remote.entities

import eu.vctrl4.business.constants.*


class CheckWithPeriodRequest()
{
    var DepartmentId:String? = null
    var CarrierDepartmentId:String? = null  
    var VehicleTypeId:String? = null  
    var TrailerTypeId:String? = null  
    var WithDriver:Boolean = true
    var RentTime:Int? = null  
    var EquipmentTime:Int? = null  
    var Mileage:Int? = null  
    var IsRent:Boolean = false  
    var OrderDates:List<String>? = null  
    var UserId:String? = SessionVars.userSession.UserId  
    var ExcludeOrderId:String? = null  
}