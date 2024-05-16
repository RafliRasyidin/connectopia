package com.rasyidin.connectopia.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Chats : Screen("chats")
    data object Chatting: Screen("chatting")
    data object Setting : Screen("setting")
    data object Status : Screen("status")
    data object Splash : Screen("splash")
    data object OnBoarding: Screen("on_board")
}