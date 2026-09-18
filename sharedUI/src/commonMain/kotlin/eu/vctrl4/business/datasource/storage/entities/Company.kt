package eu.vctrl4.business.datasource.storage.entities


import androidx.room.*
import kotlinx.serialization.*

@Serializable
@Entity(tableName = "company", inheritSuperIndices = true)
class Company : IdName()
{
    @PrimaryKey
    override var Id: String = "00000000-0000-0000-0000-000000000000"

    var Address: String? = null
    var EMail: String? = null
    var GroupId: String? = null
    var INN: Long? = null
    var IsApproved: Boolean? = null
    var IsCustomer: Boolean? = null
    override var IsDeleted: Boolean? = null
    override var isSelectedOne: Boolean = false
    var IsSupplier: Boolean? = null
    var KPP: Long? = null
    var LegalAddress: String? = null
    var LegalName: String? = null
    override var Name: String? = null
    var OwnershipType: String? = null
    var Phone: Long? = null
    var ProjectId: String? = null
    var Selected: Boolean? = null
    var UseInRVA: Boolean? = null
    var VehicleTypeGroupId: String? = null
    var WithVAT: Boolean? = null

    
    var Departments: List<Department>? = null

    override fun toString(): String
    {
        return Name?:""
    }
}