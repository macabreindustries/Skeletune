package com.example.skeletune.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.skeletune.data.model.PublicacionFeedDto;
import com.example.skletune.R;
import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.GridHeaderViewHolder> {

    private List<PublicacionFeedDto> posts;

    public ProfilePostAdapter(List<PublicacionFeedDto> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public GridHeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_grid, parent, false);
        return new GridHeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridHeaderViewHolder holder, int position) {
        PublicacionFeedDto post = posts.get(position);
        Glide.with(holder.itemView.getContext())
                .load(post.getImageUrlContent())
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_music)
                .into(holder.imgPost);
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class GridHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPost;
        public GridHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.img_post_grid);
        }
    }
}