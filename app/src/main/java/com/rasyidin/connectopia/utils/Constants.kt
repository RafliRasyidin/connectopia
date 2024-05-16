package com.rasyidin.connectopia.utils

object Constants {
    init {
        System.loadLibrary("native-lib")
    }
    external fun webClientFirebaseAPI(): String

    val WEB_CLIENT_ID = webClientFirebaseAPI()
}