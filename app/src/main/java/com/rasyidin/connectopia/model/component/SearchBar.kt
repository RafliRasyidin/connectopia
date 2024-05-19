package com.rasyidin.connectopia.model.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
                shape = RoundedCornerShape(4.dp),
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
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
            ),
            textStyle = MaterialTheme.typography.labelSmall,
            enabled = enabled,
            singleLine = true,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = text.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = CircleShape
                            )
                            .clickable {
                                text = ""
                                onQueryChange(text)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(4.dp)
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_magnifier),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text)
                }
            )
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