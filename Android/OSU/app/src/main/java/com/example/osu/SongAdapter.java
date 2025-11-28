package com.example.osu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Cancion> songList;
    private OnSongClickListener onSongClickListener;
    private static final String SERVER_ROOT_URL = "http://192.168.1.76:8080";

    public interface OnSongClickListener {
        void onSongClick(Cancion cancion);
    }

    public SongAdapter(List<Cancion> songList, OnSongClickListener onSongClickListener) {
        this.songList = songList;
        this.onSongClickListener = onSongClickListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Cancion cancion = songList.get(position);
        holder.bind(cancion, onSongClickListener);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView songImageView;
        TextView titleTextView;
        TextView artistTextView;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songImageView = itemView.findViewById(R.id.songImageView);
            titleTextView = itemView.findViewById(R.id.songTitleTextView);
            artistTextView = itemView.findViewById(R.id.songArtistTextView);
        }

        public void bind(final Cancion cancion, final OnSongClickListener listener) {
            titleTextView.setText(cancion.getTitulo());
            artistTextView.setText(cancion.getArtista());
            
            String imageUrl = cancion.getImagenUrl();
            if (imageUrl != null && imageUrl.startsWith("/")) {
                imageUrl = SERVER_ROOT_URL + imageUrl;
            }

            ImageRequest request = new ImageRequest.Builder(itemView.getContext())
                .data(imageUrl)
                .placeholder(R.mipmap.ic_launcher) // Imagen de placeholder mientras carga
                .error(R.mipmap.ic_launcher) // Imagen de error si falla la carga
                .target(songImageView)
                .build();
            Coil.imageLoader(itemView.getContext()).enqueue(request);

            itemView.setOnClickListener(v -> listener.onSongClick(cancion));
        }
    }
}