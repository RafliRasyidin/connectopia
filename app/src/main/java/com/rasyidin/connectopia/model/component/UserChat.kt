package com.rasyidin.connectopia.model.component


data class UserChat(
    val email: String,
    val username: String,
    val lastChat: String,
    val lastChatTime: String,
    val lastChatTimeStamp: Long,
    val profilePic: String,
    val isOnline: Boolean,
    val unreadChatCount: Int
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
        isOnline = true
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        unreadChatCount = 0,
        lastChatTimeStamp = 1716008744,
        isOnline = true
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        unreadChatCount = 2,
        lastChatTimeStamp = 1713416744,
        isOnline = false
    ),
    UserChat(
        email = "john.c.calhoun@examplepetstore.com",
        username = "rasyidin",
        lastChat = "Hello",
        lastChatTime = "12:00",
        profilePic = "https://picsum.photos/200/300",
        lastChatTimeStamp = 1613416744,
        unreadChatCount = 5,
        isOnline = true
    )
)
