package eu.vctrl4.storage.remote.entities

import eu.vctrl4.business.constants.*
import kotlinx.serialization.*

@Serializable
class OrderReportRequest(
    val RightsOfUserId: String? = SessionVars.userSession.UserId
     
)
{
    var RentDate:String? = null  
    var Date:String? = null  
    var CustomerCompanyIN:List<String>? = null  
    var CustomerDepartmentIN:List<String>? = null  

    val IsDeleted:Boolean =  false
}
