package com.rasyidin.connectopia.ui.screen.chats

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.data.local.AppPreferences

class ChatsViewModel(
    private val firestore: FirebaseFirestore,
    private val prefs: AppPreferences
) : ViewModel() {


}