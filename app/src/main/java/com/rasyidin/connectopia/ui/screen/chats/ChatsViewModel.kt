package com.rasyidin.connectopia.ui.screen.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.model.user.UserData
import com.rasyidin.connectopia.utils.UiEvent
import com.rasyidin.connectopia.utils.UiText
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatsViewModel(
    private val firestore: FirebaseFirestore,
    private val prefs: AppPreferences
) : ViewModel() {

    private val _addUserState: Channel<UiEvent<UserData>> = Channel()
    val addUserState = _addUserState.receiveAsFlow()

    private val _findUserState: Channel<UiEvent<UserData>> = Channel()
    val findUserState = _findUserState.receiveAsFlow()

    private val _chatsState: MutableStateFlow<UiEvent<List<UserChat>>> = MutableStateFlow(UiEvent.Idle())
    val chatsState = _chatsState.asStateFlow()

    fun getChats() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val friendsQueries = mutableListOf<Deferred<DocumentSnapshot>>()
                val chats = mutableListOf<UserChat>()
                val userData = firestore.collection("users")
                    .document(prefs.getUserId())
                    .get()
                    .await()
                    .toObject(UserData::class.java)
                if (userData != null) {
                    userData.chatList.forEach { userId ->
                        val friendsRef = firestore.collection("users").document(userId)
                        friendsQueries.add(async {friendsRef.get().await()})
                    }
                    val friendsResult = friendsQueries.awaitAll().map { it.toObject(UserData::class.java) }
                    friendsResult.forEach { friend ->
                        chats.add(
                            UserChat(
                                userId = friend?.userId.orEmpty(),
                                username = friend?.username.orEmpty(),
                                email = friend?.email.orEmpty(),
                                profilePic = friend?.profilePictureUrl.orEmpty(),
                                isOnline = friend?.online ?: false,
                            )
                        )
                    }
                    _chatsState.value = UiEvent.Success(chats)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _chatsState.value = UiEvent.Failure(message = UiText.DynamicString(e.message.toString()))
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _findUserState.send(UiEvent.Loading())
                val user = firestore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()
                    .toObjects(UserData::class.java)
                if (user.isEmpty()) {
                    _findUserState.send(UiEvent.Failure(message = UiText.StringResource(R.string.user_not_found)))
                } else {
                    _findUserState.send(UiEvent.Success(user.first()))
                }

            } catch (e: Exception) {
                _addUserState.send(UiEvent.Failure(message = UiText.DynamicString(e.message.toString())))
                e.printStackTrace()
            }
        }
    }

    fun addUserAsFriend(friendUserId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _addUserState.send(UiEvent.Loading())
                firestore.collection("users")
                    .document(prefs.getUserId())
                    .update("chatList", FieldValue.arrayUnion(friendUserId))
                    .await()
                _addUserState.send(UiEvent.Success(null))
            } catch (e: Exception) {
                _addUserState.send(UiEvent.Failure(message = UiText.DynamicString(e.message.toString())))
                e.printStackTrace()
            }
        }
    }
}