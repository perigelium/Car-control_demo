package eu.vctrl4.storage.remote.entities

data class VehicleTrackRequest
(
    val deviceId:Int?,
    val token:String?,
    val timeFrom:String?,
    val timeTo:String?,
    val url:String?
)
