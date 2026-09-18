package eu.vctrl4.business.datasource.network.main.responses

import kotlinx.serialization.*


@Serializable
class WDError()
{
    var code: Int? = null
    var msg: String? = null
}
