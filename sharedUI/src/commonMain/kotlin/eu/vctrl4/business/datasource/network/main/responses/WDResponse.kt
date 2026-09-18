package eu.vctrl4.business.datasource.network.main.responses

import kotlinx.serialization.*


@Serializable
class WDResponse<T>()
{
    var body: T? = null
    var dt: String? = null
    var ref: String? = null
    var sign: String? = null
    var error: WDError? = null

    // @SerialName("status") var statusCode: Int?
}

