package eu.vctrl4.storage.remote.entities



@kotlinx.serialization.Serializable
class DoneRequest
{
    var RequestDate: String? = null //":"2024-06-01 12:30",
    val RequestCreatorId: String? = null //":"19637199-f723-11e9-ac03-c4346bc03d84",
    var RequestCreatorName: String? = null
    var RequestComment: String? = null
}