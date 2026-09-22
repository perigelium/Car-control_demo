package eu.vctrl4.business.datasource.storage.entities

import kotlinx.serialization.Serializable

@Serializable
class OrderHistoryItem
{
    var Date: String? = null

    val State: String? = null //RD",

    val UserId: String? = null

    var Comment: String? = null

    var UserName: String? = null

    var IsProtest: Boolean = false

    var StateName: String? = null

    var ConsumerType: String? = null

    var MeasuresTaken: String? = null

    val CancelReasonId: String? = null

    var CancelReasonName: String? = null
}
