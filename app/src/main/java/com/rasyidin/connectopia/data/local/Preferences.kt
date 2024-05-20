package com.rasyidin.connectopia.data.local

interface Preferences {
    fun clearPreferences()
    fun getLoginSession(): Boolean
    fun setLoginSession(loginSession: Boolean)
    fun setOnboardingComplete(onboardingComplete: Boolean)
    fun isOnboardingComplete(): Boolean
    fun setUserId(userId: String)
    fun getUserId(): String


    companion object {
        const val PREFS_NAME = "ConnectopiaPreferences"
        const val PREFS_LOGIN_SESSION = "LOGIN_SESSION"
        const val PREFS_ONBOARDING_COMPLETE = "ONBOARDING_COMPLETE"
        const val PREFS_USER_ID = "USER_ID"
    }
}