package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
data class OrderRequest(val Id:List<String>, val ShowHistory:Boolean = false)
