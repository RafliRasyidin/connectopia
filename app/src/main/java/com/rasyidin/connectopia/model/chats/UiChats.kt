package com.rasyidin.connectopia.model.chats

import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.model.component.UserStory

data class UiChats(
    val chats: List<UserChat>,
    val stories: List<UserStory>
)
