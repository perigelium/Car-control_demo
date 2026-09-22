package eu.vctrl4.business.datasource.storage.entities

import androidx.room.*


@Entity(tableName = "vehicle_type_no_subtype", inheritSuperIndices = true, primaryKeys = ["CompanyId", "TypeId"] )
class VehicleTypeNoSubtype
{
    
    var CompanyId:String = "00000000-0000-0000-0000-000000000000"
    
    var TypeId:String = "00000000-0000-0000-0000-000000000000"
    var TypeName:String? = null

    
    var OptionTypes:List<IdNameValueName>? = null

    override fun toString(): String
    {
        return TypeName?:""
    }
}
