package com.example.agis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<String> chatMessages;

    public ChatAdapter(List<String> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.chatMessage.setText(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
        }
    }
}
