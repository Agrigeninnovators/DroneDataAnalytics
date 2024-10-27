package com.example.agis

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agis.ui.theme.ColorModelMessage
import com.example.agis.ui.theme.ColorUserMessage

class ChatAIMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppContent()
        }
    }

    @Composable
    fun ChatAppContent() {
        val viewModel: ChatViewModel = viewModel()
        Column(modifier = Modifier.fillMaxSize()) {
            AppHeader()
            MessageList(
                modifier = Modifier.weight(1f),
                messageList = viewModel.messageList
            )
            MessageInput(onMessageSend = { viewModel.sendMessage(it) })
        }
    }

    @Composable
    fun AppHeader() {
        Text(
            text = "ChatBot",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun MessageList(messageList: List<MessageModel>, modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            reverseLayout = true
        ) {
            if (messageList.isEmpty()) {
                item {
                    NoMessagesView()
                }
            } else {
                items(messageList.reversed()) { message ->
                    MessageRow(messageModel = message)
                }
            }
        }
    }

    @Composable
    fun NoMessagesView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Chat,
                contentDescription = "ChatBot Icon",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ask me Anything",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Composable
    fun MessageRow(messageModel: MessageModel) {
        val isModel = messageModel.role == "model"
        val backgroundColor = if (isModel) ColorModelMessage else ColorUserMessage
        val textColor = if (isModel) Color.Black else Color.White

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = if (isModel) Arrangement.Start else Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = messageModel.message,
                    color = textColor,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    @Composable
    fun MessageInput(onMessageSend: (String) -> Unit) {
        var message by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = message,
                onValueChange = { message = it },
                label = { Text("Type your message") }
            )
            IconButton(onClick = {
                if (message.isNotEmpty()) {
                    onMessageSend(message)
                    message = ""
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}
