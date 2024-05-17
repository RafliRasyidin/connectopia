package com.rasyidin.connectopia.ui.screen.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.model.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OnBoardingViewModel(private val db: FirebaseFirestore) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        viewModelScope.launch {
            try {
                _signInState.update { signState ->
                    if (result.data != null) {
                        db.collection("users").document(result.data.userId).set(result.data).await()
                    }
                    signState.copy(isSignedInSuccessful = result.data != null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _signInState.update { signState ->
                    signState.copy(isSignedInSuccessful = false, signInError = e.message)
                }
            }

        }
    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}