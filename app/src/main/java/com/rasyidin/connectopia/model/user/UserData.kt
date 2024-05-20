package com.rasyidin.connectopia.model.user

import com.rasyidin.connectopia.model.component.UserStory

data class UserData(
    val userId: String = "",
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val chatList: List<String> = emptyList(),
    val blockedUsers: List<String> = emptyList(),
    val stories: List<UserStory> = emptyList(),
    val isOnline: Boolean = false,
)