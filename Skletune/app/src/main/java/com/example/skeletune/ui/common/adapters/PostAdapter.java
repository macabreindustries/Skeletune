package com.example.skeletune.ui.common.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skeletune.data.model.PublicacionFeedDto;
import com.example.skletune.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PublicacionFeedDto> posts;
    private Context context;
    private OnPostInteractionListener listener;

    // Interface para manejar las acciones desde el Fragmento
    public interface OnPostInteractionListener {
        void onLikeClick(PublicacionFeedDto post, int position);
        void onCommentClick(PublicacionFeedDto post);
    }

    public PostAdapter(List<PublicacionFeedDto> posts, Context context, OnPostInteractionListener listener) {
        this.posts = posts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_card, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PublicacionFeedDto post = posts.get(position);

        holder.tvNombre.setText(post.getNombreUsuario());
        holder.tvTiempo.setText(post.getTiempoPublicacion());
        holder.tvBody.setText(post.getTextoBody());
        holder.tvLikes.setText(post.getLikesCount() + " me gusta");
        holder.tvComments.setText(post.getCommentsCount() + " comentarios");

        // --- Lógica del Corazón (Like) ---
        if (post.isLikedByMe()) {
            holder.imgLike.setColorFilter(Color.RED);
            // Si tienes un ic_like_filled podrías usar: holder.imgLike.setImageResource(R.drawable.ic_like_filled);
        } else {
            holder.imgLike.setColorFilter(Color.BLACK);
        }

        // --- Clic en Like ---
        holder.imgLike.setOnClickListener(v -> {
            if (listener != null) listener.onLikeClick(post, position);
        });

        // --- Clic en Comentario ---
        holder.imgComment.setOnClickListener(v -> {
            if (listener != null) listener.onCommentClick(post);
        });

        // GLIDE: Avatar
        Glide.with(context)
                .load(post.getAvatarUrl())
                .circleCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(holder.imgAvatar);

        // GLIDE: Imagen Post
        if (post.getImageUrlContent() != null) {
            holder.imgPost.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(post.getImageUrlContent())
                    .centerCrop()
                    .into(holder.imgPost);
        } else {
            holder.imgPost.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() { return posts.size(); }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgPost, imgLike, imgComment;
        TextView tvNombre, tvTiempo, tvBody, tvLikes, tvComments;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.image_user_avatar);
            imgPost = itemView.findViewById(R.id.image_post_content);
            imgLike = itemView.findViewById(R.id.icon_like);      // Asegúrate que este ID coincida con tu XML
            imgComment = itemView.findViewById(R.id.icon_comment); // Asegúrate que este ID coincida con tu XML
            tvNombre = itemView.findViewById(R.id.text_post_header);
            tvTiempo = itemView.findViewById(R.id.text_post_time);
            tvBody = itemView.findViewById(R.id.text_post_body);
            tvLikes = itemView.findViewById(R.id.text_like_count);
            tvComments = itemView.findViewById(R.id.text_comment_count);
        }
    }
}