package com.example.osu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongSelectionActivity extends AppCompatActivity implements SongAdapter.OnSongClickListener {

    private static final String TAG = "SongSelectionActivity";
    private static final String SERVER_ROOT_URL = "http://192.168.1.76:8080";
    private static final String API_URL = SERVER_ROOT_URL + "/skeletune/api/";

    private CancionApiService apiService;
    private RecyclerView songsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_selection);
        
        songsRecyclerView = findViewById(R.id.songsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient(API_URL).create(CancionApiService.class);

        fetchCanciones();
    }

    private void fetchCanciones() {
        apiService.getCanciones().enqueue(new Callback<List<Cancion>>() {
            @Override
            public void onResponse(Call<List<Cancion>> call, Response<List<Cancion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setupRecyclerView(response.body());
                } else {
                    Toast.makeText(SongSelectionActivity.this, "Error al cargar canciones: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cancion>> call, Throwable t) {
                Toast.makeText(SongSelectionActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView(List<Cancion> songList) {
        SongAdapter songAdapter = new SongAdapter(songList, this);
        songsRecyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onSongClick(Cancion cancion) {
        fetchChartsForSong(cancion);
    }

    private void fetchChartsForSong(final Cancion cancion) {
        apiService.getChartsForSong(cancion.getIdCancion()).enqueue(new Callback<List<ChartMania>>() {
            @Override
            public void onResponse(Call<List<ChartMania>> call, Response<List<ChartMania>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<ChartMania> charts = response.body();
                    if (charts.size() > 1) {
                        showDifficultyDialog(charts, cancion.getUrlAudio());
                    } else {
                        fetchNotesForChart(charts.get(0), cancion.getUrlAudio());
                    }
                } else {
                    Toast.makeText(SongSelectionActivity.this, "No se encontraron dificultades para esta canción (código: " + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChartMania>> call, Throwable t) {
                Toast.makeText(SongSelectionActivity.this, "Fallo de conexión al buscar dificultades: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // --- MENÚ DE DIFICULTAD RESTAURADO A LA VERSIÓN SIMPLE Y FUNCIONAL ---
    private void showDifficultyDialog(final List<ChartMania> charts, final String urlAudio) {
        String[] difficulties = new String[charts.size()];
        for (int i = 0; i < charts.size(); i++) {
            difficulties[i] = charts.get(i).getDificultad().name();
        }

        new AlertDialog.Builder(this)
                .setTitle("Elige una dificultad")
                .setItems(difficulties, (dialog, which) -> {
                    fetchNotesForChart(charts.get(which), urlAudio);
                })
                .show();
    }

    private void fetchNotesForChart(final ChartMania chart, final String urlAudio) {
        apiService.getNotesForChart(chart.getIdChartMania()).enqueue(new Callback<List<NotaMania>>() {
            @Override
            public void onResponse(Call<List<NotaMania>> call, Response<List<NotaMania>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chart.setNotas(response.body());
                    startGame(chart, urlAudio);
                } else {
                    Toast.makeText(SongSelectionActivity.this, "Error al cargar las notas del chart (código: " + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotaMania>> call, Throwable t) {
                Toast.makeText(SongSelectionActivity.this, "Fallo de conexión al cargar notas: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startGame(ChartMania chart, String urlAudio) {
        String fullAudioUrl = urlAudio;

        if (urlAudio != null && urlAudio.startsWith("/")) {
            fullAudioUrl = SERVER_ROOT_URL + urlAudio;
        }

        if (fullAudioUrl != null && (fullAudioUrl.contains("youtube.com") || fullAudioUrl.contains("youtu.be"))) {
            Toast.makeText(this, "Las URLs de YouTube no se pueden reproducir directamente.", Toast.LENGTH_LONG).show();
            return; 
        }

        Log.d(TAG, "Iniciando juego con URL de audio: " + fullAudioUrl);

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("CHART", (Serializable) chart);
        intent.putExtra("URL_AUDIO", fullAudioUrl);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}