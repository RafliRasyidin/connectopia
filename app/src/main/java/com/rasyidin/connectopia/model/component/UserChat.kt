package com.rasyidin.connectopia.model.component


data class UserChat(
    val userId: String = "",
    val email: String = "",
    val username: String = "",
    val lastChat: String = "",
    val lastChatTime: String = "",
    val lastChatTimeStamp: Long = 0,
    val profilePic: String = "",
    val isOnline: Boolean = false,
    val unreadChatCount: Int = 0
)

val dummyUserChatLists = listOf(
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        unreadChatCount = 1,
        lastChatTimeStamp = System.currentTimeMillis(),
        isOnline = true,
        userId = "1"
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        unreadChatCount = 0,
        lastChatTimeStamp = 1716008744,
        isOnline = true,
        userId = "1"
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        unreadChatCount = 2,
        lastChatTimeStamp = 1713416744,
        isOnline = false,
        userId = "1"
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        lastChatTimeStamp = 1613416744,
        unreadChatCount = 5,
        isOnline = true,
        userId = "1"
    )
)
