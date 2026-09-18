package eu.vctrl4.business.datasource.storage.entities


import androidx.room.*
import kotlinx.serialization.*

@Serializable
@Entity(tableName = "vehicle_option", inheritSuperIndices = true)
class VehicleOption
{
    
    @PrimaryKey
    var Id:String = "00000000-0000-0000-0000-000000000000"  
    var Name:String? = null  
    var IsDeleted:Boolean? = null  

    
    var EnumOptions:List<IdNameValueName>? = null  

    override fun toString(): String
    {
        return Name?:""
    }
}
