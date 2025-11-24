package com.example.osu;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

public class GameActivity extends AppCompatActivity implements ManiaGameView.OnGameCompletionListener {

    private static final String TAG = "GameActivity";
    private ManiaGameView maniaGameView;
    private ChartMania chart;
    private String urlAudio;
    private MediaPlayer mediaPlayer;
    private boolean gameFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        maniaGameView = findViewById(R.id.maniaGameView);
        maniaGameView.setOnGameCompletionListener(this);

        ImageButton pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(v -> showPauseDialog());

        chart = (ChartMania) getIntent().getSerializableExtra("CHART");
        urlAudio = getIntent().getStringExtra("URL_AUDIO");

        if (chart != null && chart.getNotas() != null && !chart.getNotas().isEmpty()) {
            maniaGameView.setChart(chart);
            maniaGameView.startGame();
        } else {
            Toast.makeText(this, "Error: El chart no contiene notas.", Toast.LENGTH_LONG).show();
            finish(); 
            return;
        }

        if (urlAudio != null && !urlAudio.isEmpty()) {
            prepareAndPlayAudio(urlAudio);
        } else {
            Log.e(TAG, "No se recibió la URL del audio.");
        }
    }

    private void showPauseDialog() {
        // Pausamos el juego y la música
        maniaGameView.pauseGame();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

        new MaterialAlertDialogBuilder(this)
            .setTitle("Pausa")
            .setCancelable(false) // Impide que se cierre al tocar fuera
            .setPositiveButton("Reanudar", (dialog, which) -> {
                // Reanudamos el juego y la música
                maniaGameView.resumeGame();
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            })
            .setNegativeButton("Salir", (dialog, which) -> {
                // Terminamos la partida y vamos a los resultados
                onGameFinished();
            })
            .show();
    }

    private void prepareAndPlayAudio(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        
        mediaPlayer.setAudioAttributes(
            new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        );

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "MediaPlayer Error. What: " + what + ", Extra: " + extra);
            Toast.makeText(getApplicationContext(), "Error al reproducir el audio.", Toast.LENGTH_SHORT).show();
            return true;
        });

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(mp -> {
                Log.d(TAG, "Audio preparado. Iniciando reproducción.");
                mp.start();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Canción terminada.");
                onGameFinished();
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, "Error al configurar el MediaPlayer", e);
            Toast.makeText(this, "Error de red o formato de audio no válido.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGameCompleted() {
        new android.os.Handler(getMainLooper()).postDelayed(this::onGameFinished, 1000);
    }

    private void onGameFinished() {
        if (gameFinished) return;
        gameFinished = true;

        if (isFinishing()) return;

        maniaGameView.stopGame();

        Intent intent = new Intent(this, ResultsActivity.class);

        intent.putExtra("ID_CHART", chart.getIdChartMania());
        intent.putExtra("SCORE", maniaGameView.getScore());
        intent.putExtra("MAX_COMBO", maniaGameView.getMaxCombo());
        intent.putExtra("PERFECTS", maniaGameView.getPerfects());
        intent.putExtra("GREATS", maniaGameView.getGreats());
        intent.putExtra("GOODS", maniaGameView.getGoods());
        intent.putExtra("MISSES", maniaGameView.getMisses());

        startActivity(intent);
        finish(); 
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Se sobreescribe el botón de "atrás" del sistema para que también pause el juego
    @Override
    public void onBackPressed() {
        showPauseDialog();
    }

    // Ya no se necesitan onPause/onResume porque el diálogo de pausa se encarga de todo
}
