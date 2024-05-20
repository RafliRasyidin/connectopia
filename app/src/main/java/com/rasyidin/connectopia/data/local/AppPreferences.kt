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

    override fun setOnboardingComplete(onboardingComplete: Boolean) {
        with(prefs.edit()) {
            putBoolean(Preferences.PREFS_ONBOARDING_COMPLETE, onboardingComplete)
            apply()
        }
    }

    override fun isOnboardingComplete(): Boolean {
        return prefs.getBoolean(Preferences.PREFS_ONBOARDING_COMPLETE, false)
    }

    override fun setUserId(userId: String) {
        with(prefs.edit()) {
            putString(Preferences.PREFS_USER_ID, userId)
            apply()
        }
    }

    override fun getUserId(): String {
        return prefs.getString(Preferences.PREFS_USER_ID, "").orEmpty()
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