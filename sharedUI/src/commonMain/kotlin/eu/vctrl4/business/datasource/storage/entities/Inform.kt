package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.Serializable


@Serializable
class Inform
{
    var Name: String? = null     
    var Type: String? = null    // EML or SMS
    var Address: String? = null    // e-mail or phone number (string)
}
