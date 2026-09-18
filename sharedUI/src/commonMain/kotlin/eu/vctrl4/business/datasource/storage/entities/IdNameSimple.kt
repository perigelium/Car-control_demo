package eu.vctrl4.business.datasource.storage.entities


class IdNameSimple()
{
    var Id:String? = null
    var Name:String? = null

    constructor(id: String?, Name: String?) : this()
    {
        this.Id = id
        this.Name = Name
    }

    override fun toString(): String
    {
        return Name?:""
    }
}