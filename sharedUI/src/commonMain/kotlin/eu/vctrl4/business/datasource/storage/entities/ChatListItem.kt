package eu.vctrl4.storage.remote.chats.entities



@kotlinx.serialization.Serializable
class ChatListItem
{
    var AuthorId: String? = null  
    var AuthorName: String? = null //": "TEST",

    var Id: String? = null  

    var Name: String? = null //": ,

    var Users: List<ChatUser?> = ArrayList() //":

    var CreatedAt: String? = null  
    var UpdatedAt: String? = null  

    var Unread: Boolean = true  

    //var orderId: String? = null
    var companyId:String? = null
    var orderNumber:String? = null
    var orderDate: String? = null
}
