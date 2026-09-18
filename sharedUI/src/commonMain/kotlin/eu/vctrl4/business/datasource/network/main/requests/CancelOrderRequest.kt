package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
data class CancelOrderRequest( val CancelOrderId:String, val Comment:String?, val OrderId:String, val UserId:String)
