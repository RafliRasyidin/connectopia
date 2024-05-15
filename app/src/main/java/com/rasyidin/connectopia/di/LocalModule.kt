package com.rasyidin.connectopia.di

import android.content.Context
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.data.local.Preferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val modulePreferences = module {
    single { androidApplication().getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE) }
    single { AppPreferences(get()) }
}