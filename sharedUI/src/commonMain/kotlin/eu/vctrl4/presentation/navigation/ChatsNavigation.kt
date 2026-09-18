package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface ChatsNavigation
{
    @Serializable
    data object ChatsList : ChatsNavigation

    @Serializable
    data class Chat(val strChatListItem:String?, val chatId:String?) : ChatsNavigation

    @Serializable
    data class ChatUsers(val chatUsers:List<String>) : ChatsNavigation
}