package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.*
import kotlin.uuid.*

@Serializable
class IdNameValueName()
{
    var Id:String = "00000000-0000-0000-0000-000000000000"
    var Name:String? = null
    var IsDeleted: Boolean? = null

    var valueName:String? = null
    var isChecked:Boolean? = null

    @OptIn(ExperimentalUuidApi::class)
    constructor(Name: String?, valueName: String?) : this()
    {
        this.Id = Uuid.random().toString()
        this.Name = Name
        this.valueName = valueName
    }

    override fun toString(): String
    {
        return Name?:""
    }
}