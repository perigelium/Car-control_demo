package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.*

@Serializable
class AddressPoint(
    var Address: String, //
    var Latitude:Float, //
    var Longitude:Float, //
    var PointId:String, //
    var Type:String //
) 
{
    var CityName:String? = null // ,
    var ParentId:String? = null // ,
    var RegionName:String? = null // ,
}
