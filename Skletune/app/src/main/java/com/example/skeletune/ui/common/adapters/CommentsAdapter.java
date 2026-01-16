package com.example.skeletune.ui.common.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.skeletune.data.model.ComentarioResponseDto;
import com.example.skletune.R;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<ComentarioResponseDto> mComments;

    public CommentsAdapter(List<ComentarioResponseDto> comments) {
        this.mComments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Asegúrate de que el nombre del XML sea exactamente el que creaste
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        ComentarioResponseDto comment = mComments.get(position);

        holder.tvUser.setText(comment.getNombreUsuario());
        holder.tvBody.setText(comment.getComentario());
        holder.tvTime.setText(comment.getFechaRelativa());

        Glide.with(holder.itemView.getContext())
                .load(comment.getAvatarUrl())
                .circleCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() { return mComments.size(); }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUser, tvBody, tvTime;

        public CommentViewHolder(@NonNull View v) {
            super(v);
            imgAvatar = v.findViewById(R.id.image_comment_avatar);
            tvUser = v.findViewById(R.id.text_comment_user);
            tvBody = v.findViewById(R.id.text_comment_body);
            tvTime = v.findViewById(R.id.text_comment_time);
        }
    }
}