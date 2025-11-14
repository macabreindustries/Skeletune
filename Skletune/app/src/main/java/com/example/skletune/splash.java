package com.example.skletune;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    private int repeatCount = 0; // Contador de repeticiones
    private static final int MAX_REPEATS = 3; // Número de veces que se reproducirá el video

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_inicio);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(mp -> {
            repeatCount++;
            if (repeatCount < MAX_REPEATS) {
                videoView.start(); // Reproduce de nuevo
            } else {
                navigateToLogin(); // Después de 3 repeticiones, pasa al login
            }
        });

        videoView.start(); // Primera reproducción
    }

    private void navigateToLogin() {
        Intent intent = new Intent(splash.this, InicioSesion.class);
        startActivity(intent);
        finish(); // Evita que el usuario regrese al splash
    }
}
