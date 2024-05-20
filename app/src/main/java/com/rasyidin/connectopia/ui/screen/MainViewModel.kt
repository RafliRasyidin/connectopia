package com.rasyidin.connectopia.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.data.local.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel(
    private val firestore: FirebaseFirestore,
    private val prefs: AppPreferences
) : ViewModel() {

    fun setOnlineSession(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isLoggedIn = prefs.getLoginSession()
                if (isLoggedIn) {
                    firestore.collection("users")
                        .document(prefs.getUserId())
                        .update("isOnline", isOnline)
                        .await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}