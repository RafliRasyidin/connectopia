package com.rasyidin.connectopia.ui.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.ui.component.BottomNavBar
import com.rasyidin.connectopia.ui.component.ComposableLifecycle
import com.rasyidin.connectopia.ui.navigation.Screen
import com.rasyidin.connectopia.ui.screen.chats.ChatsScreen
import com.rasyidin.connectopia.ui.screen.chatting.ChattingScreen
import com.rasyidin.connectopia.ui.screen.login.LoginScreen
import com.rasyidin.connectopia.ui.screen.on_board.OnBoardingScreen
import com.rasyidin.connectopia.ui.screen.setting.SettingScreen
import com.rasyidin.connectopia.ui.screen.splash.SplashScreen
import com.rasyidin.connectopia.ui.screen.status.StatusScreen
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import com.rasyidin.connectopia.utils.hideStatusBar
import com.rasyidin.connectopia.utils.showStatusBar
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectopiaTheme {
                ConnectopiaApp()
            }
        }
    }
}

@Composable
fun ConnectopiaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    prefs: AppPreferences = koinInject(),
    viewModel: MainViewModel = koinViewModel(),
) {
    var bottomNavBarState by rememberSaveable { mutableStateOf(false) }
    val navBackStack by navController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()

    ComposableLifecycle { _, event ->
        if (event == ON_START) {
            viewModel.setOnlineSession(true)
        }
        if (event == ON_STOP) {
            viewModel.setOnlineSession(false)
        }
    }
    bottomNavBarState = when (navBackStack?.destination?.route) {
        Screen.Chats.route, Screen.Status.route, Screen.Setting.route -> true
        else -> false
    }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (bottomNavBarState) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Splash.route,
            builder = {
                composable(Screen.Login.route) {
                    systemUiController.isStatusBarVisible = true
                    LoginScreen()
                }
                composable(Screen.OnBoarding.route) {
                    systemUiController.isStatusBarVisible = true
                    OnBoardingScreen(navController = navController)
                }
                composable(Screen.Chats.route) {
                    systemUiController.isStatusBarVisible = true
                    LaunchedEffect(Unit) { bottomNavBarState = true }
                    ChatsScreen(navController = navController)
                }
                composable(Screen.Status.route) {
                    LaunchedEffect(Unit) { bottomNavBarState = true }
                    StatusScreen()
                }
                composable(Screen.Setting.route) {
                    LaunchedEffect(Unit) { bottomNavBarState = true }
                    SettingScreen(navController = navController)
                }
                composable(Screen.Chatting.route) {
                    LaunchedEffect(Unit) { bottomNavBarState = false }
                    ChattingScreen()
                }
                composable(Screen.Splash.route) {
                    SplashScreen()
                    systemUiController.isStatusBarVisible = false
                    LaunchedEffect(Unit) {
                        bottomNavBarState = false
                        delay(1000)
                        val isLoggedIn = prefs.getLoginSession()
                        if (!isLoggedIn) {
                            navController.navigate(Screen.OnBoarding.route) {
                                popUpTo(Screen.Splash.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            return@LaunchedEffect
                        }
                        navController.navigate(Screen.Chats.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            }
        )
    }
}
