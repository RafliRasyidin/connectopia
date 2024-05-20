package com.rasyidin.connectopia.model.user

data class UserData(
    val userId: String = "",
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val chatList: List<String> = emptyList(),
    val blockedUsers: List<String> = emptyList(),
    val isOnline: Boolean = false,
)