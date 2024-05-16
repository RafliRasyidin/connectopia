package com.rasyidin.connectopia.ui.screen.on_board

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnBoardingViewModel : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _signInState.update { it.copy(
            isSignedInSuccessful = result.data != null
        ) }
    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}