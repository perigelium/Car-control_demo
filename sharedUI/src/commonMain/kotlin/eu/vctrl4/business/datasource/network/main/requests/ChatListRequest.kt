package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
class ChatListRequest {
	var ChatId: String? =
		null
	var ModifyTime: String? =
		null
	var UserId: String? =
		null
	var IsPublic: Boolean? = null
}
