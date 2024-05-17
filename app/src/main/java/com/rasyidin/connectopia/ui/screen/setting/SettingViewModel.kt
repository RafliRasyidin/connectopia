package com.rasyidin.connectopia.ui.screen.setting

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.model.component.ProfileSetting
import com.rasyidin.connectopia.model.user.UserData
import com.rasyidin.connectopia.ui.screen.on_board.GoogleAuthUiClient
import com.rasyidin.connectopia.ui.screen.on_board.SignOutResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SettingViewModel(
    private val authClient: GoogleAuthUiClient,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _signOutState: MutableStateFlow<SignOutResult> = MutableStateFlow(SignOutResult())
    val signOutState = _signOutState.asStateFlow()

    private val _userDataState: MutableStateFlow<UserData> = MutableStateFlow(UserData())
    val userDataState = _userDataState.asStateFlow()

    init {
        getUserData()
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authClient.signOut()
            withContext(Dispatchers.Main) {
                _signOutState.value = result
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            authClient.getSignedInUser()?.run {
                val data = db.collection("users").document(userId).get().await().toObject(UserData::class.java)
                if (data != null) {
                    _userDataState.value = data
                }
            }
        }
    }

}