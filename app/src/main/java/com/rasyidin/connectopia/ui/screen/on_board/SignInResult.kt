package com.rasyidin.connectopia.ui.screen.on_board

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class SignInState(
    val isSignedInSuccessful: Boolean = false,
    val signInError: String? = null
)

data class UserData(
    val userId: String,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null
)
