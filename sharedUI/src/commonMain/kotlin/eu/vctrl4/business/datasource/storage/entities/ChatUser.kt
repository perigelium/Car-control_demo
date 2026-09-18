package eu.vctrl4.storage.remote.chats.entities


import kotlinx.serialization.*

@kotlinx.serialization.Serializable
class ChatUser(@SerialName("Id") var userId: String? = null, @SerialName("FullName") var userName: String? = null, var avatarPath: String? = null)
	//: IUser
{
    //private var Id: String? = null //": "d6fd5945-8b54-11e6-80b9-9c8e991e5cb0",
    //var admin: Boolean? = null //": true,
    //private var Name: String? = null //": "TEST"

    var LastSeenAt: String? = null

/*    override fun getAvatar(): String?
    {
        return avatarPath
    }

    override fun setAvatar(avatarPath: String)
    {
        this.avatarPath = avatarPath
    }

    override fun getId(): String?
    {
        return userId
    }

    override fun setId(userId: String)
    {
        this.userId = userId
    }

    override fun getName(): String?
    {
        return userName
    }

    override fun setName(userName: String)
    {
        this.userName = userName
    }*/
}
