package com.rasyidin.connectopia.model.chats

data class MessageRepliedTo(
    val text: String,
    val mediaUrl: String?,
    val isSeen: Boolean,
    val fileName: String?,
    val curUserReaction: String?,
    val otherUserReaction: String?,
    val senderId: String,
    val starred: Boolean,
    val time: Long,
)
