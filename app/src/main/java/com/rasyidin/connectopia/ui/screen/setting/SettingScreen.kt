package com.rasyidin.connectopia.ui.screen.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.rasyidin.connectopia.model.component.ProfileSetting
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
        },
        onEditClick = {

        },
        onSettingClick = { profileSetting ->
            
        }
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    userData: UserData,
    onLogoutClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onSettingClick: (ProfileSetting) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(id = R.string.account),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserProfile(
                modifier = Modifier.padding(horizontal = 12.dp),
                userData = userData,
                onEditClick = onEditClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileSettings(
                onClick = onSettingClick
            )
            Spacer(modifier = Modifier.weight(1F))
            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(id = R.string.logout),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    userData: UserData,
    onEditClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = userData.username.orEmpty(),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = userData.bio ?: "-",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onEditClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userData.email.orEmpty(),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(id = R.string.email),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier,
    onClick: (ProfileSetting) -> Unit = {}
) {
    val settings = listOf(
        ProfileSetting(
            title = stringResource(id = R.string.blocked_users),
            body = stringResource(id = R.string.manage_blocked_users),
            isExpandable = false
        ),
        ProfileSetting(
            title = stringResource(id = R.string.chat_customisations),
            body = stringResource(id = R.string.personalize_your_chat_experience),
            isExpandable = false
        ),
        ProfileSetting(
            title = stringResource(id = R.string.upload_quality),
            body = stringResource(id = R.string.adjust_image_and_status_quality),
            isExpandable = true
        ),
    )
    settings.forEachIndexed { index, profileSetting ->
        if (index != 0) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        ItemProfileSetting(
            modifier = modifier,
            setting = profileSetting,
            onClick = onClick
        )
    }
}

@Composable
fun ItemProfileSetting(
    modifier: Modifier = Modifier,
    setting: ProfileSetting,
    onClick: (ProfileSetting) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    var slidePosition by remember { mutableFloatStateOf(50F) }
    var quality by remember { mutableStateOf(slidePosition.toInt().toString()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(setting)
                if (setting.isExpandable) {
                    isExpanded = !isExpanded
                }
            }
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = setting.body,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                painter = painterResource(id = if (isExpanded) R.drawable.ic_chevron_down else R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        }
        AnimatedVisibility(
            visible = setting.isExpandable && isExpanded,
            enter = expandVertically(
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ),
            exit = shrinkVertically(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            )
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.upload_quality_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.quality_image, quality),
                    style = MaterialTheme.typography.titleMedium
                )
                Slider(
                    value = slidePosition,
                    onValueChange = {
                        slidePosition = it
                        quality = slidePosition.toInt().toString()
                    },
                    valueRange = 50F..100F,
                    steps = 9
                )
            }

        }

        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        HorizontalDivider(thickness = .5.dp, color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Preview
@Composable
fun PreviewSettingContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        SettingContent(
            userData = UserData(
                username = "Rafli Rasyidin",
                bio = "Android Developer",
                profilePictureUrl = "https://picsum.photos/200",
                email = "john.mckinley@examplepetstore.com",
                phoneNumber = "08123456789",
            )
        )
    }
}