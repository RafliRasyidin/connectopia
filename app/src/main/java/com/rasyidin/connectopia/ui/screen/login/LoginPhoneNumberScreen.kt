package com.rasyidin.connectopia.ui.screen.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ConnectopiaTheme {
        LoginScreen()
    }
}