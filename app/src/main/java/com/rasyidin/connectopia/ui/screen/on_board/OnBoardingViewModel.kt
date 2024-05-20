package com.rasyidin.connectopia.ui.screen.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.model.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OnBoardingViewModel(
    private val db: FirebaseFirestore,
    private val prefs: AppPreferences
) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (result.data == null) {
                    _signInState.update { signState ->
                        signState.copy(isSignedInSuccessful = false)
                    }
                    return@launch
                }
                _signInState.update { signState ->
                    val userData = getUserData(result.data.userId)
                    if (userData != null) {
                        db.collection("users").document(userData.userId).set(userData).await()
                    } else {
                        db.collection("users").document(result.data.userId).set(result.data).await()
                    }
                    prefs.setUserId(result.data.userId)
                    signState.copy(isSignedInSuccessful = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _signInState.update { signState ->
                    signState.copy(isSignedInSuccessful = false, signInError = e.message)
                }
            }

        }
    }

    private suspend fun getUserData(userId: String): UserData? {
        try {
            val userData = db.collection("users").document(userId).get().await().toObject(UserData::class.java)
            return userData
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}