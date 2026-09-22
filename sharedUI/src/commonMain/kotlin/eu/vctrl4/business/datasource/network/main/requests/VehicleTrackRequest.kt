package eu.vctrl4.business.datasource.network.main.requests

data class VehicleTrackRequest
(
    val deviceId:Int?,
    val token:String?,
    val timeFrom:String?,
    val timeTo:String?,
    val url:String?
)
