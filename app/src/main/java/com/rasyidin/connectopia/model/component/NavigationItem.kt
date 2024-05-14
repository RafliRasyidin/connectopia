package com.rasyidin.connectopia.model.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.ui.navigation.Screen

data class NavigationItem(
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int,
    @StringRes val name: Int,
    val screen: Screen,
    var isActive: Boolean = false
)

val navigationItems = listOf(
    NavigationItem(
        icon = R.drawable.ic_chats_outlined,
        iconSelected = R.drawable.ic_chats_filled,
        name = R.string.chats,
        screen = Screen.Chats
    ),
    NavigationItem(
        icon = R.drawable.ic_status_outlined,
        iconSelected = R.drawable.ic_status_filled,
        name = R.string.status,
        screen = Screen.Status
    ),
    NavigationItem(
        icon = R.drawable.ic_setting_outlined,
        iconSelected = R.drawable.ic_setting_filled,
        name = R.string.settings,
        screen = Screen.Setting
    ),
)