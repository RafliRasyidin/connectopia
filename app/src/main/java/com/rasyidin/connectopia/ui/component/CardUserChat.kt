package com.rasyidin.connectopia.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rasyidin.connectopia.R
import com.rasyidin.connectopia.model.component.UserChat
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme
import com.rasyidin.connectopia.utils.DateUtils

@Composable
fun CardUserChat(
    modifier: Modifier = Modifier,
    chat: UserChat,
    onClick: (UserChat) -> Unit = {},
) {
    val context = LocalContext.current
    val time = DateUtils.getLastChatTime(chat.lastChatTimeStamp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(chat) }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomStart
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(chat.profilePic)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                if (chat.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .offset((34).dp, (-34).dp)
                            .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                            .background(
                                color = Color.Green,
                                shape = CircleShape
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Row {
                    Text(
                        text = chat.username,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1F)
                            .padding(top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Row {
                    Text(
                        text = chat.lastChat,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(1F)
                            .padding(top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (chat.unreadChatCount > 0) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = CircleShape
                                )
                        ) {
                            Text(
                                text = chat.unreadChatCount.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = .5.dp, color = MaterialTheme.colorScheme.outline)
    }
}

@Preview(showBackground = true)
@Composable
private fun CardContactPreview() {
    ConnectopiaTheme {
        CardUserChat(
            chat = UserChat(
                email = "john.c.calhoun@examplepetstore.com",
                username = "rasyidin",
                lastChat = "Hello",
                lastChatTime = "12:00",
                profilePic = "https://picsum.photos/200/300",
                unreadChatCount = 2,
                lastChatTimeStamp = 1713416744,
                isOnline = true,
                userId = ""
            )
        )
    }
}