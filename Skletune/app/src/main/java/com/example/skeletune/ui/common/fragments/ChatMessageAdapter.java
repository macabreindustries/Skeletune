package com.example.skeletune.ui.common.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.skletune.R;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> messages;
    private final String otherUserAvatarUrl;

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public ChatMessageAdapter(List<ChatMessage> messages, String otherUserAvatarUrl) {
        this.messages = messages;
        this.otherUserAvatarUrl = otherUserAvatarUrl;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        if (message.isSentByUser()) {
            return VIEW_TYPE_SENT;
        }
        return VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).bind(message);
        } else {
            ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder for sent messages
    class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessageBody;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            tvMessageBody = itemView.findViewById(R.id.tv_message_body);
        }

        void bind(ChatMessage message) {
            tvMessageBody.setText(message.getMessage());
        }
    }

    // ViewHolder for received messages
    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessageBody;
        ImageView ivAvatar;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            tvMessageBody = itemView.findViewById(R.id.tv_message_body);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
        }

        void bind(ChatMessage message) {
            tvMessageBody.setText(message.getMessage());
            Glide.with(itemView.getContext())
                    .load(otherUserAvatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_alumno)
                    .into(ivAvatar);
        }
    }
}
