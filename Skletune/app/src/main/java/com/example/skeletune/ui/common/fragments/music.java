package com.example.skeletune.ui.common.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.skeletune.data.model.Cancion;
import com.example.skeletune.ui.common.viewmodels.MusicViewModel;
import com.example.skletune.R;

import java.io.IOException;

public class music extends Fragment {

    private MusicViewModel musicViewModel;
    private ImageView imageAlbum;
    private TextView tvSongName, tvArtistName, tvLikes, tvViews, tvSwipes;
    private ImageButton btnMatch, btnReject, btnPlay, btnSkip, btnRestart; // btnRestart es el de la izquierda
    private SeekBar progressBar;

    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    private int idCancionActual = -1;
    private boolean isUserSeeking = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        // 1. Inicializar UI
        imageAlbum = view.findViewById(R.id.image_album);
        tvSongName = view.findViewById(R.id.song_name);
        tvArtistName = view.findViewById(R.id.artist_name);
        tvLikes = view.findViewById(R.id.likes_count);
        tvViews = view.findViewById(R.id.views_count);
        //tvSwipes = view.findViewById(R.id.swipes_count);
        progressBar = view.findViewById(R.id.progress_bar);

        // --- REASIGNACIÓN DE BOTONES SEGÚN TU DESCRIPCIÓN ---

        // El de la IZQUIERDA (icon3) - Reinicia la canción
        btnRestart = view.findViewById(R.id.button_skip);

        // El del CENTRO (ID: button_like) - Ahora es el PLAY/PAUSA
        btnPlay = view.findViewById(R.id.button_like);

        // El de la DERECHA (ID: button_play) - Ahora es el SKIP (Siguiente)
        btnSkip = view.findViewById(R.id.button_play);

        // Los de abajo (Tinder style)
        btnReject = view.findViewById(R.id.button_reject);    // Tache grande -> Siguiente
        btnMatch = view.findViewById(R.id.button_match);      // Corazón grande -> Favoritos

        // 2. Inicializar ViewModel
        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        // 3. Observar la canción
        musicViewModel.getCancionActual().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null && resource.status == com.example.skeletune.data.network.Resource.Status.SUCCESS) {
                if (resource.data != null) pintarCancion(resource.data);
            }
        });

        // --- 4. LÓGICA DE BOTONES ---

        // BOTÓN PLAY (Centro - button_like): Pausa y reanuda
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(getContext(), "Pausa", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.start();
                    Toast.makeText(getContext(), "Reproduciendo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // BOTÓN SKIP (Derecha - button_play): Pasa a la siguiente canción
        btnSkip.setOnClickListener(v -> {
            if (idCancionActual != -1) {
                musicViewModel.cargarCancionAleatoria();
            }
        });

        // BOTÓN REJECT (Tache Grande): También pasa a la siguiente canción
        btnReject.setOnClickListener(v -> {
            if (idCancionActual != -1) {
                musicViewModel.cargarCancionAleatoria();
            }
        });

        // BOTÓN MATCH (Corazón Grande): Solo mensaje de favorito
        btnMatch.setOnClickListener(v -> {
            if (idCancionActual != -1) {
                musicViewModel.darLike(idCancionActual);
                Toast.makeText(getContext(), "❤️ ¡Agregada a tus favoritos!", Toast.LENGTH_SHORT).show();
            }
        });

        // BOTÓN RESTART (Izquierda - icon3): Reinicia la canción
        btnRestart.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                Toast.makeText(getContext(), "Reiniciando canción", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. SeekBar interactiva
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    int duration = mediaPlayer.getDuration();
                    int newPosition = (duration * progress) / 100;
                    mediaPlayer.seekTo(newPosition);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { isUserSeeking = true; }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { isUserSeeking = false; }
        });

        musicViewModel.cargarCancionAleatoria();
        return view;
    }

    private void pintarCancion(Cancion cancion) {
        idCancionActual = cancion.getIdCancion();
        tvSongName.setText(cancion.getTitulo());
        tvArtistName.setText(cancion.getArtista());
        tvLikes.setText("❤️ " + (cancion.getLikesCount() != null ? cancion.getLikesCount() : 0) + " Likes");
        tvViews.setText("👁 " + (cancion.getViewsCount() != null ? cancion.getViewsCount() : 0) + " Views");
        //tvSwipes.setText("🔁 " + (cancion.getSwipesCount() != null ? cancion.getSwipesCount() : 0) + " Swipes");

        Glide.with(this).load(cancion.getImagenUrl()).into(imageAlbum);
        reproducirAudio(cancion.getUrlAudio());
        musicViewModel.registrarVista(idCancionActual);
    }

    private void reproducirAudio(String url) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                actualizarProgresoSeekBar();
            });
            mediaPlayer.setOnCompletionListener(mp -> musicViewModel.cargarCancionAleatoria());
            mediaPlayer.setOnErrorListener((mp, what, extra) -> true);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void actualizarProgresoSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying() && !isUserSeeking) {
            int pos = mediaPlayer.getCurrentPosition();
            int dur = mediaPlayer.getDuration();
            if (dur > 0) progressBar.setProgress((pos * 100) / dur);
        }
        mHandler.postDelayed(this::actualizarProgresoSeekBar, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) { mediaPlayer.release(); mediaPlayer = null; }
        mHandler.removeCallbacksAndMessages(null);
    }
}