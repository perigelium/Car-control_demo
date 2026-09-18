package eu.vctrl4.business.datasource.network.main.responses

import kotlinx.serialization.*

@Serializable
class Role
{
    var ClientOptions: MutableList<String?>? = null
    var CompanyId: String? = null
    var DefaultDepartmentId: String? = null
    var DepartmentId: String? = null
    var ExtFunctions: MutableList<String?>? = null
    var Function: String? = null // MGR
    var RegistrationDate: String? = null
    var RoleId: String? = null
}
