package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.Serializable


@Serializable
class ExtParameters
{
    var Route: String? = null  
    var Purpose: String? = null     
    var Slingers: String? = null  
    var AreaOfWork: String? = null     
    var CargoWidth: Float? = null     
    var CargoHeight: Float? = null     
    var CargoLength: Float? = null     
    var Responsible: String? = null     
    var NatureOfWork: String? = null     
    var ShippingName: String? = null     
    var CargoOversize: Boolean = false     
    var ReturnNextDay: Boolean? = null     
    var IsLongDuration: Boolean? = null     
    var WorkNearPowerLines: Boolean? = null     
    var MotorCadeName: String? = null  
}
