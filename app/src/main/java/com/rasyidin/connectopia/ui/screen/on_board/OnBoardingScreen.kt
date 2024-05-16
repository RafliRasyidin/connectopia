package com.rasyidin.connectopia.ui.screen.on_board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.data.local.AppPreferences
import com.rasyidin.connectopia.ui.navigation.Screen
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import org.koin.compose.koinInject

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    prefs: AppPreferences = koinInject(),
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Image(
                painter = painterResource(id = R.drawable.img_peoples_chats),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1F)
                    .padding(horizontal = 48.dp)
            )
            Text(
                text = stringResource(id = R.string.on_board_message),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1F))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = {
                    prefs.setOnboardingComplete(true)
                    navController.navigate(Screen.Login.route)
                }
            ) {
                Text(text = "Login With Phone Number",)
            }
            Text(
                text = "Or",
                modifier = Modifier.padding(bottom = 8.dp),
            )
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                onClick = {
                    prefs.setOnboardingComplete(true)
                    navController.navigate(Screen.Login.route)
                }
            ) {
                Text(text = "Login With Google",)
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOnBoardingScreen(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        OnBoardingScreen(
            navController = rememberNavController()
        )
    }
}