package com.rasyidin.connectopia.ui.screen.on_board

import com.rasyidin.connectopia.model.user.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class SignInState(
    val isSignedInSuccessful: Boolean = false,
    val signInError: String? = null
)
