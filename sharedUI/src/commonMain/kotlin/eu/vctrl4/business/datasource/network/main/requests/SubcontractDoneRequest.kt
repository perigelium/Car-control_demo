package eu.vctrl4.storage.remote.entities

import eu.vctrl4.business.constants.*
import kotlinx.serialization.*

@Serializable
data class SubcontractDoneRequest
    (
    val OrderId: String,  

    val State: Char  
)
{
    var UserId: String? = SessionVars.userSession.UserId  
}
