package com.example.agis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.agis.databinding.ActivityChatbotBinding;
import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private ActivityChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private List<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatbotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);

        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecyclerView.setAdapter(chatAdapter);

        binding.sendButton.setOnClickListener(v -> {
            String message = binding.messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                chatMessages.add("User: " + message);
                chatMessages.add("Bot: " + generateResponse(message));
                chatAdapter.notifyDataSetChanged();
                binding.messageInput.setText("");
            }
        });
    }

    private String generateResponse(String message) {
        // Simple logic for demonstration; adjust with FAQs/response logic.
        if (message.toLowerCase().contains("crop")) {
            return "For crop information, visit the plot details.";
        } else {
            return "Thank you for reaching out. Our team will get back to you soon.";
        }
    }
}
