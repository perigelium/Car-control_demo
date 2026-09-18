package eu.vctrl4.storage.entities



@kotlinx.serialization.Serializable
class Inform
{
    var Name: String? = null     
    var Type: String? = null    // EML or SMS
    var Address: String? = null    // e-mail or phone number (string)
}
