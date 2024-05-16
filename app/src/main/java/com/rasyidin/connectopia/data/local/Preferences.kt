package com.rasyidin.connectopia.data.local

interface Preferences {
    fun clearPreferences()
    fun getLoginSession(): Boolean
    fun setLoginSession(loginSession: Boolean)

    companion object {
        const val PREFS_NAME = "ConnectopiaPreferences"
        const val PREFS_LOGIN_SESSION = "LOGIN_SESSION"
    }
}