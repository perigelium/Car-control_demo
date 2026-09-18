package eu.vctrl4.storage.remote.entities

import eu.vctrl4.business.constants.*
import kotlinx.serialization.*

@Serializable
data class UserIdObj(val UserId: String? = SessionVars.userSession.UserId, val OrderId: String? = SessionVars.fromPushVariables.subcontractOrderId)