package com.rasyidin.connectopia.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    enabled: Boolean = true,
    onSearch: (String) -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {}
) {
    var text by remember { mutableStateOf(value) }
    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
    ) {
        TextField(
            modifier = Modifier
                .weight(1F)
                .onFocusChanged { onFocusChange(it.isFocused) },
            value = text,
            onValueChange = { newText ->
                text = newText
                onQueryChange(text)
            },

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchBar() {
    ConnectopiaTheme {
        SearchBar(
            hint = "Ini adalah hint"
        )
    }
}