package eu.vctrl4.storage.entities

import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*


@Entity(tableName = "vehicle_type_subtype", inheritSuperIndices = true, primaryKeys = ["CompanyId", "TypeId", "SubtypeId"] )
class VehicleTypeWithSubtype
{
    
    var CompanyId:String = "00000000-0000-0000-0000-000000000000"
    
    var TypeId:String = "00000000-0000-0000-0000-000000000000"
    var TypeName:String? = null
    
    var SubtypeId:String = "00000000-0000-0000-0000-000000000000"
    var SubtypeName:String? = null

    
    var OptionTypes:List<IdNameValueName>? = null

    override fun toString(): String
    {
        return SubtypeName?:""
    }
}
