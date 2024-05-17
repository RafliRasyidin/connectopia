package com.rasyidin.connectopia.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.connectopia.ui.screen.on_board.GoogleAuthUiClient
import com.rasyidin.connectopia.ui.screen.on_board.SignOutResult
import com.rasyidin.connectopia.ui.screen.on_board.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingViewModel(
    private val authClient: GoogleAuthUiClient
) : ViewModel() {

    private val _signOutState: MutableStateFlow<SignOutResult> = MutableStateFlow(SignOutResult())
    val signOutState = _signOutState.asStateFlow()

    private val _userDataState: MutableStateFlow<UserData> = MutableStateFlow(UserData())
    val userDataState = _userDataState.asStateFlow()

    init {
        getSignedUserData()
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authClient.signOut()
            withContext(Dispatchers.Main) {
                _signOutState.value = result
            }
        }
    }

    fun getSignedUserData() {
        authClient.getSignedInUser()?.run {
            _userDataState.value = this
        }
    }

}