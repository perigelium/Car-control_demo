package eu.vctrl4.storage.remote.entities

import eu.vctrl4.business.constants.SessionVars.userSession
import kotlinx.serialization.*

@Serializable
class SameUsersRequest
{
    var UserId: String? = userSession.UserId

    var OrderId: String? = null

    var DepartmentId: String? = null
    var Functions: List<String>? = listOf("DSP") //  Dictionary.GetRoleFunctionTypes

    val ByCompany:Boolean = true
}
