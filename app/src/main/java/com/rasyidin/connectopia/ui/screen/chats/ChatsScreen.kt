package com.rasyidin.connectopia.ui.screen.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.model.component.SearchBar
import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.model.component.dummyUserChatLists
import com.rasyidin.connectopia.ui.component.CardUserChat
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme

@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    ChatsContent(modifier = modifier)
}

@Composable
fun ChatsContent(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(id = R.string.chats),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 12.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(18.dp)
                            .size(16.dp)
                    )
                }
            }
            Chats(
                chats = dummyUserChatLists,
                modifier = Modifier.padding(horizontal = 12.dp),
                onClick = {

                }
            )
        }
    }
}

@Composable
fun Chats(
    modifier: Modifier = Modifier,
    chats: List<UserChat>,
    onClick: (UserChat) -> Unit
) {
    LazyColumn(
        modifier =  modifier.fillMaxWidth()
    ) {
        items(chats) { chat ->
            CardUserChat(
                chat = chat,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatsContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        ChatsContent()
    }
}
