package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.*


@Serializable
class Department : IdName()
{
    override var Id: String = "00000000-0000-0000-0000-000000000000"
    override var Name: String? = null

    var ClientOptions: List<String>? = null
    //var Division: String? = null  // Int ?
    var ExpirationDate: String? = null
    var HasChildren: Boolean? = null
    override var IsDeleted: Boolean? = null
    override var isSelectedOne: Boolean = false
    var IsUnit: Boolean? = null
    var MotorCadeId: String? = null
    var MotorCadeName: String? = null
    var ParentId: String? = null
    var Selected: Boolean? = null

    var LegalName: String? = null
    var IsSupplier: Boolean? = false

    var CompanyId: String? = null

    override fun toString(): String
    {
        return Name?:""
    }
}