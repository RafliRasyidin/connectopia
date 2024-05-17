package com.rasyidin.connectopia.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.model.user.UserData
import com.rasyidin.connectopia.ui.navigation.Screen
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import com.rasyidin.connectopia.utils.showShortToast
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = koinViewModel(),
    prefs: AppPreferences = koinInject(),
    navController: NavHostController
) {
    val userData by viewModel.userDataState.collectAsStateWithLifecycle()
    val signOutState by viewModel.signOutState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = signOutState.isSuccessful) {
        if (signOutState.isSuccessful) {
            context.showShortToast(context.getString(R.string.logout_successful))
            prefs.setLoginSession(false)
            navController.navigate(Screen.OnBoarding.route) {
                popUpTo(Screen.OnBoarding.route) {
                    inclusive = true
                }
            }
        }
    }
    SettingContent(
        modifier = modifier,
        userData = userData,
        onLogoutClick = {
            viewModel.logout()
        }
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    userData: UserData,
    onLogoutClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserProfile(userData = userData,)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLogoutClick
            ){
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    userData: UserData,
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(userData.profilePictureUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userData.username.orEmpty(),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = userData.email ?: "-",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        SettingContent(
            userData = UserData()
        )
    }
}