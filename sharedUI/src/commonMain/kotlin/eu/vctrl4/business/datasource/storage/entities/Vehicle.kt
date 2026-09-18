package eu.vctrl4.storage.entities

class Vehicle
{
    var AsatCode: String? = null
    var Capacity: Float? = null
    var Comment: String? = null
    var CompanyId: String? = null
    var ConditionId: Any? = null
    var DepartmentId: String? = null
    var Id: String? = null
    var IsDeleted: Boolean? = null
    var ModelId: String? = null
    var ModelName: String? = null
    var Name: String? = null
    var NumberOfPassengers: Int? = null
    var Options: List<VehicleOptionType>? = null
    var OrderType: String? = null

    //var Projects: List<Project>? = null
    //var ReadyStates: List<ReadyState>? = null

    var ReadyToWork: Any? = null
    var RegNumber: String? = null

    var SubTypeId: String? = null
    var SubTypeName: String? = null

    var SubclassId: String? = null
    var SubclassName: String? = null

    var VehicleTypeId: String? = null
    var VehicleTypeName: String? = null

    override fun toString(): String
    {
        return Name?:""
    }
}
