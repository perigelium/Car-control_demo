package eu.vctrl4.storage.entities

import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*


@kotlinx.serialization.Serializable
@Entity(tableName = "nature_of_work", inheritSuperIndices = true)
class NatureOfWork(@PrimaryKey override var Id: String, override var Name: String?
) : IdName()
{
    override var IsDeleted: Boolean? = null
    override var isSelectedOne:Boolean = false

    override fun toString():String
    {
        return Name?:""
    }
}