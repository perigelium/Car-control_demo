package eu.vctrl4.business.datasource.network.common


import kotlinx.serialization.*


@Serializable
data class MainGenericResponse<T>(
    @SerialName("result") var result: T?,
    @SerialName("status") var statusCode: Int?,
    @SerialName("alert") var alert: JAlertResponse? = JAlertResponse(),
)