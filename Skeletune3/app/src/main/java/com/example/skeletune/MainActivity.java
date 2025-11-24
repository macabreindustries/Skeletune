package com.example.skeletune;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int playCount = 0; // Contador de reproducciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView video = findViewById(R.id.videoLogo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_inicio);
        video.setVideoURI(uri);
        video.start();

        // Listener para cuando termina el video
        video.setOnCompletionListener(mp -> {
            playCount++; // Incrementa el contador
            if (playCount < 3) {
                video.start(); // Reproduce de nuevo
            } else {
                // Cuando llegue a 3, pasa a la siguiente actividad
                Intent intent = new Intent(MainActivity.this, SegundaPantalla.class);
                startActivity(intent);
                finish(); // Cierra la splash screen
            }
        });
    }
}
