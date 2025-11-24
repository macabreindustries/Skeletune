package com.example.osu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundManager = SoundManager.getInstance(this);

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(v -> {
            soundManager.playSound(SoundManager.SOUND_UI_CLICK);
            Intent intent = new Intent(MainActivity.this, SongSelectionActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}
