package eu.vctrl4.business.datasource.network.main.requests

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
