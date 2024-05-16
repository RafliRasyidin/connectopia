package com.rasyidin.connectopia.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.data.local.Preferences
import com.rasyidin.connectopia.ui.screen.on_board.GoogleAuthUiClient
import com.rasyidin.connectopia.ui.screen.on_board.OnBoardingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePreferences = module {
    single { androidApplication().getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE) }
    single { AppPreferences(get()) }
}

val moduleViewModel = module {
    single { GoogleAuthUiClient(oneTapClient = Identity.getSignInClient(androidApplication())) }
    viewModel { OnBoardingViewModel() }
}