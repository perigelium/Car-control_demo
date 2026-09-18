package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.*

@Serializable
class Session()
{
    var SessionKey: String? = null // e84f0728-5a4b-49f3-95fb-e5f3327db7f1
    var UserId: String? = null // 415c9cf3-2e0b-11e6-80c8-10604ba895d8
    var IsCustomer: Boolean? = null
    var IsSupplier: Boolean? = null

    var Profile: UserProfile? = null

    var ProjectCode: String? = null //RHD, SVHD

    var code: Int? = null
}