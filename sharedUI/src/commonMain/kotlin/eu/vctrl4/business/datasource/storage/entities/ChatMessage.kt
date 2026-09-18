package eu.vctrl4.storage.remote.chats.entities

import kotlinx.serialization.*
import kotlin.uuid.*

@kotlinx.serialization.Serializable
class ChatMessage
{
    //var createTime: String? = null
    //var updateTime:String? = null

    var userName: String? = null

    var AuthorId: String? = null

    var CompanyId:String? = null

    var Id: String? = null // incoming

    var ChatId:String? = null // outgoing

    @SerialName("Content")
    var msgText: String? = null

    @SerialName("CreatedAt")
    var strCreatedAt:String? = null // "2023-12-25T10:58:21.925Z",

    @SerialName("UpdatedAt")
    var strUpdatedAt:String? = null // "2023-12-25T10:58:21.925Z"

    var chatUser: ChatUser? = null

    //var createdDateTime: Date? = null

    fun getUser(): ChatUser
    {
        return if (chatUser != null) chatUser!! else ChatUser(AuthorId, userName, null)
    }

    fun setText(msgText: String?)
    {
        this.msgText = msgText
    }

/*    override fun getCreatedAt(): Date
    {
        if (createdDateTime == null)
        {
            createdDateTime = toDateTime(strCreatedAt)
        }
        return createdDateTime!!
    }

    fun setCreatedAt(createdAt: Date)
    {
        this.createdDateTime = createdAt
        if (createTime == null)
        {
            createTime = toServerDateTime(createdAt.time, false)
        }
    }*/
}
