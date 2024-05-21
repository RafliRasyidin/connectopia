package com.rasyidin.connectopia.ui.screen.chats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.model.component.Stories
import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.model.component.UserStory
import com.rasyidin.connectopia.model.component.dummyStories
import com.rasyidin.connectopia.model.component.dummyUserChatLists
import com.rasyidin.connectopia.model.user.UserData
import com.rasyidin.connectopia.ui.component.CardUserChat
import com.rasyidin.connectopia.ui.component.ComposableLifecycle
import com.rasyidin.connectopia.ui.component.InputTextField
import com.rasyidin.connectopia.ui.component.SearchBar
import com.rasyidin.connectopia.ui.navigation.Screen
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import com.rasyidin.connectopia.utils.UiEvent
import com.rasyidin.connectopia.utils.showShortToast
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ChatsViewModel = koinViewModel(),
) {
    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.getChats()
        }
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val addUserState by viewModel.addUserState.collectAsStateWithLifecycle(
        initialValue = UiEvent.Idle(),
    )
    val findUserState by viewModel.findUserState.collectAsStateWithLifecycle(
        initialValue = UiEvent.Idle(),
    )
    val chatsState by viewModel.chatsState.collectAsStateWithLifecycle()
    ChatsContent(
        modifier = modifier,
        onClickChat = { userStory ->
            navController.navigate(Screen.Chatting.route)
        },
        onClickStory = { userChat ->

        },
        onClickAdd = { showBottomSheet = true },
        chatsState = chatsState
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            dragHandle = {
                HandleSheetAddUser()
            }
        ) {
            BottomSheetAddUserContent(
                addUserState = addUserState,
                onClickFind = { email ->
                    viewModel.getUserByEmail(email)
                },
                onClickAddFriend = { userId ->
                    viewModel.addUserAsFriend(userId)
                },
                findUserState = findUserState,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                }
            )
        }
    }
}

@Composable
fun ChatsContent(
    modifier: Modifier = Modifier,
    chatsState: UiEvent<List<UserChat>>,
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
                Spacer(modifier = Modifier.width(12.dp))
            }
            if (chatsState is UiEvent.Failure) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = chatsState.message.asString())
                }
            }
            if (chatsState is UiEvent.Success) {
                Chats(
                    chats = chatsState.data ?: emptyList(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { userChat ->
                        onClickChat(userChat)
                    }
                )
            }

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
fun HandleSheetAddUser(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.add_new_user),
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

@Composable
fun BottomSheetAddUserContent(
    modifier: Modifier = Modifier,
    addUserState: UiEvent<UserData>,
    findUserState: UiEvent<UserData>,
    onClickFind: (String) -> Unit = {},
    onClickAddFriend: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var enabled by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    if (findUserState is UiEvent.Failure) {
        context.showShortToast(findUserState.message.asString())
    }
    if (addUserState is UiEvent.Failure) {
        context.showShortToast(addUserState.message.asString())
    }
    if (addUserState is UiEvent.Success) {
        context.showShortToast(context.getString(R.string.success_add_friend))
        onDismiss()
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 12.dp),
    ) {
        if (findUserState is UiEvent.Success) {
            val userData = findUserState.data
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userData?.profilePictureUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = userData?.username.orEmpty(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = userData?.email.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClickAddFriend(userData?.userId ?: "")
                },
                enabled = true
            ) {
                if (addUserState is UiEvent.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(text = stringResource(id = R.string.add_friend))
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.input_email_user),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputTextField(
                onQueryChange = { newText ->
                    email = newText
                    enabled = email.isNotEmpty()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClickFind(email)
                },
                enabled = enabled
            ) {
                if (findUserState is UiEvent.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(text = stringResource(id = R.string.find))
                }

            }
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
            onClickAdd = {},
            chatsState = UiEvent.Success(dummyUserChatLists)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomSheetAddUserContent(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        BottomSheetAddUserContent(
            addUserState = UiEvent.Success(UserData()),
            findUserState = UiEvent.Success(UserData()),
        )
    }
}
