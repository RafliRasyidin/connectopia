package com.rasyidin.connectopia.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.data.local.Preferences
import com.rasyidin.connectopia.ui.screen.chats.ChatsViewModel
import com.rasyidin.connectopia.ui.screen.on_board.GoogleAuthUiClient
import com.rasyidin.connectopia.ui.screen.on_board.OnBoardingViewModel
import com.rasyidin.connectopia.ui.screen.setting.SettingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePreferences = module {
    single { androidApplication().getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE) }
    single { AppPreferences(get()) }
}

val moduleViewModel = module {
    single { Firebase.firestore }
    single { Firebase.auth }
    single { GoogleAuthUiClient(oneTapClient = Identity.getSignInClient(androidApplication()), auth = get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { SettingViewModel(get(), get(), get()) }
    viewModel { ChatsViewModel(get(), get()) }
}