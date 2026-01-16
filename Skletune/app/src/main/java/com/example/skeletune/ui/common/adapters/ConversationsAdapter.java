package com.example.skeletune.ui.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.skeletune.data.model.Conversation;
import com.example.skletune.R;

import java.util.List;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder> {

    private final List<Conversation> conversations;
    private final Context context;
    private final OnConversationClickListener listener;

    // Interfaz para manejar los clics
    public interface OnConversationClickListener {
        void onConversationClick(Conversation conversation);
    }

    public ConversationsAdapter(Context context, List<Conversation> conversations, OnConversationClickListener listener) {
        this.context = context;
        this.conversations = conversations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        Conversation conversation = conversations.get(position);
        holder.bind(conversation, listener);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAvatar;
        private final TextView tvUserName;
        private final TextView tvLastMessage;
        private final TextView tvTimestamp;
        private final View unreadDot;

        ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
            unreadDot = itemView.findViewById(R.id.unread_dot);
        }

        void bind(final Conversation conversation, final OnConversationClickListener listener) {
            tvUserName.setText(conversation.getOtherUser().getUsername());
            tvLastMessage.setText(conversation.getLastMessage().getMensaje());
            tvTimestamp.setText("1 d"); // Placeholder

            if (!conversation.getLastMessage().isVisto()) {
                unreadDot.setVisibility(View.VISIBLE);
            } else {
                unreadDot.setVisibility(View.GONE);
            }

            Glide.with(context)
                .load(conversation.getOtherUser().getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(ivAvatar);

            // Hacer que toda la fila sea clicable
            itemView.setOnClickListener(v -> listener.onConversationClick(conversation));
        }
    }
}
