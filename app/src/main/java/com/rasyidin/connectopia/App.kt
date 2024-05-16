package com.rasyidin.connectopia

import android.app.Application
import com.rasyidin.connectopia.di.modulePreferences
import com.rasyidin.connectopia.di.moduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(modulePreferences, moduleViewModel)
        }
    }
}