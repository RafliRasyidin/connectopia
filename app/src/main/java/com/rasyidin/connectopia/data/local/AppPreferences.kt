package com.rasyidin.connectopia.data.local

import android.content.SharedPreferences

class AppPreferences(
    private val prefs: SharedPreferences
) : Preferences {
    override fun clearPreferences() {
        with(prefs.edit()) {
            clear()
            apply()
        }
    }

    override fun getLoginSession(): Boolean {
        return prefs.getBoolean(Preferences.PREFS_LOGIN_SESSION, false)
    }

    override fun setLoginSession(loginSession: Boolean) {
        with(prefs.edit()) {
            putBoolean(Preferences.PREFS_LOGIN_SESSION, loginSession)
            apply()
        }
    }
}