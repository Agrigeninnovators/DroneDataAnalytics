package com.example.agis

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class ChatViewModel : ViewModel() {
    val messageList = mutableStateListOf<MessageModel>()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro", // Replace with your model name
        apiKey = Constants.apiKey // Ensure you have your API key set in Constants
    )

    fun sendMessage(message: String) {
        messageList.add(MessageModel(message, "user")) // Add user's message

        // Simulating a loading state by adding a "typing" message
        messageList.add(MessageModel("Typing...", "model"))

        viewModelScope.launch {
            try {
                // Send the user's message to the AI model and get a response
                val response = generativeModel.startChat(
                    history = messageList.map { content(it.role) { text(it.message) } }
                ).sendMessage(message)

                // Adjust the following line based on how the response is structured
                val responseText = response.text // Use the appropriate method/property to access the text

                // Remove the "typing" message and add the actual response
                messageList.removeAt(messageList.lastIndex) // Remove "Typing..." message
                responseText?.let { MessageModel(it, "model") }?.let { messageList.add(it) } // Add AI model's response
            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex) // Remove "Typing..." message
                messageList.add(MessageModel("Error: ${e.message}", "model")) // Show error message
            }
        }
    }
}
