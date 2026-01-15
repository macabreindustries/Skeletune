package com.example.skeletune.ui.common.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skeletune.ui.auth.InicioSesion;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

public class splash extends AppCompatActivity {

    private int repeatCount = 0;
    private static final int MAX_REPEATS = 2;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);
        VideoView videoView = findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_inicio);

        if (videoView != null) {
            videoView.setVideoURI(video);

            videoView.setOnCompletionListener(mp -> {
                repeatCount++;
                if (repeatCount < MAX_REPEATS) {
                    videoView.start();
                } else {
                    finalizarSplash();
                }
            });
            videoView.start();
        }
    }

    private void finalizarSplash() {
        Intent intent;
        // Verificamos la sesión en silencio mientras el usuario veía el video
        if (sessionManager.getUserId() != -1) {
            intent = new Intent(splash.this, NavigationHostActivity.class);
        } else {
            intent = new Intent(splash.this, InicioSesion.class);
        }
        startActivity(intent);
        finish();
    }
}