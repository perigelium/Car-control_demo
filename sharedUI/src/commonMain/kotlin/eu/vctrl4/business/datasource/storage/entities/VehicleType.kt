package eu.vctrl4.business.datasource.storage.entities


import androidx.room.*
import kotlinx.serialization.*

@Serializable
@Entity(tableName = "vehicle_type", inheritSuperIndices = true)
class VehicleType
{
    
    @PrimaryKey
    var Id: String = "00000000-0000-0000-0000-000000000000"   

    var Name: String? = null  
    var Picture: String? = null  
    var OrderType: String? = null  
    var OrderTypeName: String? =
        null  
    var IsDeleted: Boolean? = null  
    var IsTrailer: Boolean? = null  
    var HasChildren: Boolean? = null  

    
    var OptionTypes: List<IdNameValueName>? = null  

    
    var TypeGroups: List<IdNameValueName>? = null

    var VehicleTypeGroupId: String? = null
    var IsNeedSpecialVehicleFields: Boolean? = null

}
