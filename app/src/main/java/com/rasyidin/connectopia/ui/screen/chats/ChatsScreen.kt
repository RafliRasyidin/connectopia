package com.rasyidin.connectopia.ui.screen.chats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.model.component.Stories
import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.model.component.UserStory
import com.rasyidin.connectopia.model.component.dummyStories
import com.rasyidin.connectopia.model.component.dummyUserChatLists
import com.rasyidin.connectopia.ui.component.CardUserChat
import com.rasyidin.connectopia.ui.component.InputTextField
import com.rasyidin.connectopia.ui.component.SearchBar
import com.rasyidin.connectopia.ui.navigation.Screen
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    ChatsContent(
        modifier = modifier,
        onClickChat = { userStory ->
            navController.navigate(Screen.Chatting.route)
        },
        onClickStory = { userChat ->

        },
        onClickAdd = { showBottomSheet = true }
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                ) {
                    Text(
                        text = "Add New User",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        thickness = .5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        ) {
            BottomSheetAddUserContent(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showBottomSheet = false
                    }
                }
            )
        }
    }
}

@Composable
fun ChatsContent(
    modifier: Modifier = Modifier,
    onClickStory: (UserStory) -> Unit,
    onClickChat: (UserChat) -> Unit,
    onClickAdd: () -> Unit
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
            UsersStories(
                stories = dummyStories,
                modifier = Modifier.padding(start = 12.dp),
                onClick = { userStory ->
                    onClickStory(userStory)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = .5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
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
                FloatingActionButton(
                    shape = RoundedCornerShape(8.dp),
                    onClick = { onClickAdd() },
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(18.dp)
                            .size(16.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
            Chats(
                chats = dummyUserChatLists,
                modifier = Modifier.padding(horizontal = 12.dp),
                onClick = { userChat ->
                    onClickChat(userChat)
                }
            )
        }
    }
}

@Composable
fun UsersStories(
    modifier: Modifier = Modifier,
    stories: List<Stories>,
    onClick: (UserStory) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(stories) { story ->
            Story(story = story, onClick = { onClick(it) })
        }
    }
}

@Composable
fun Story(
    modifier: Modifier = Modifier,
    story: Stories,
    onClick: (UserStory) -> Unit = {},
) {
    val context = LocalContext.current
    val lastStoryUnseen = story.stories.findLast { it.isViewed } ?: story.stories.last()
    Column(
        modifier = modifier
            .padding(end = 16.dp)
            .clickable { onClick(lastStoryUnseen) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .border(
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(story.stories.last().story)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
fun Chats(
    modifier: Modifier = Modifier,
    chats: List<UserChat>,
    onClick: (UserChat) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(chats) { chat ->
            CardUserChat(
                chat = chat,
                onClick = onClick
            )
        }
    }
}

@Composable
fun BottomSheetAddUserContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var enabled by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 12.dp),
    ) {
        Text(
            text = "Input email user",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            onQueryChange = { newText ->
                enabled = newText.isNotEmpty()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = enabled
        ) {
            Text(
                text = "Submit"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewChatsContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        ChatsContent(
            modifier = modifier,
            onClickStory = {},
            onClickChat = {},
            onClickAdd = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetAddUserContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        BottomSheetAddUserContent()
    }
}
