package eu.vctrl4.storage.remote.entities

import kotlinx.serialization.*

@Serializable
data class OrderRequest(val Id:List<String>, val ShowHistory:Boolean = false)
