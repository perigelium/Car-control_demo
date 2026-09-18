package eu.vctrl4.storage.entities


import androidx.room.*

@Entity(tableName = "vehicle_model", inheritSuperIndices = true)
class VehicleModel
{
    
    @PrimaryKey
    var Id: String = "00000000-0000-0000-0000-000000000000"   

    var Name:String? = null  
    var Capacity:Float? = null  
    var IsDeleted:Boolean? = null  
    var NumberOfPass:Int? = null  
    var VehicleTypeId:String? = null  
    var VehicleBrandId:String? = null
}