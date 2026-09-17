package com.externalgram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExternalgramApp()
                }
            }
        }
    }
}

@Composable
fun ExternalgramApp() {
    var currentScreen by remember { mutableStateOf("chats") }
    var selectedChat by remember { mutableStateOf("") }

    when (currentScreen) {
        "chats" -> {
            ChatsScreen(
                onChatClick = { chatName ->
                    selectedChat = chatName
                    currentScreen = "chat"
                },
                onSettingsClick = {
                    currentScreen = "settings"
                }
            )
        }

        "chat" -> {
            ChatScreen(
                chatName = selectedChat,
                onBackClick = {
                    currentScreen = "chats"
                }
            )
        }

        "settings" -> {
            SettingsScreen(
                onBackClick = {
                    currentScreen = "chats"
                }
            )
        }
    }
}

data class ChatItem(
    val name: String,
    val message: String,
    val time: String,
    val color: Color
)

@Composable
fun ChatsScreen(
    onChatClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val chats = listOf(
        ChatItem(
            name = "Артём",
            message = "Привет! Как дела?",
            time = "21:30",
            color = Color(0xFF4CAF50)
        ),
        ChatItem(
            name = "Externalgram News",
            message = "Добро пожаловать в Externalgram",
            time = "20:15",
            color = Color(0xFF2196F3)
        ),
        ChatItem(
            name = "Рабочая группа",
            message = "Новое сообщение",
            time = "19:48",
            color = Color(0xFFFF9800)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Externalgram",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Поиск"
                        )
                    }

                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Настройки"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F2FF))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    tint = Color(0xFF1976D2)
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Демонстрационный режим",
                    color = Color(0xFF1976D2),
                    fontSize = 14.sp
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(chats) { chat ->
                    ChatRow(
                        chat = chat,
                        onClick = {
                            onChatClick(chat.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ChatRow(
    chat: ChatItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(chat.color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.name.take(1),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = chat.message,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Text(
            text = chat.time,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }

    Divider()
}

@Composable
fun ChatScreen(
    chatName: String,
    onBackClick: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(
                "Привет! Это демонстрационный чат Externalgram.",
                "Здесь позже будут реальные сообщения Telegram."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = chatName,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "был недавно",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE8F2FF),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(12.dp)
                        ) {
                            Text(text = message)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text("Сообщение")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.size(8.dp))

                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages = messages + messageText
                            messageText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Отправить",
                        tint = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    var streamerMode by remember { mutableStateOf(false) }
    var filtersEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Настройки")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Внешний вид",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Размер текста")
            Text(
                text = "Стандартный",
                color = Color.Gray
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Функции Externalgram",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    streamerMode = !streamerMode
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (streamerMode) {
                        "Streamer Mode: включён"
                    } else {
                        "Streamer Mode: выключен"
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    filtersEnabled = !filtersEnabled
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (filtersEnabled) {
                        "Текстовые фильтры: включены"
                    } else {
                        "Текстовые фильтры: выключены"
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Настройка шрифтов")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Встроенный переводчик")
            }
        }
    }
}