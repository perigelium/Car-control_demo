package eu.vctrl4.business.datasource.network.common


import kotlinx.serialization.*


@Serializable
data class JAlertResponse(
    @SerialName("title") var title: String = "",
    @SerialName("message") var message: String = ""
)