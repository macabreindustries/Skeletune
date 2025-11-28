package com.example.osu;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundManager {

    private static SoundManager instance;
    private SoundPool soundPool;
    private HashMap<String, Integer> soundMap = new HashMap<>();
    private boolean isLoaded = false;

    // Nombres que usaremos para identificar los sonidos
    public static final String SOUND_UI_CLICK = "ui_click";
    public static final String SOUND_NOTE_HIT = "note_hit";

    private SoundManager(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
            if (status == 0) {
                isLoaded = true;
            }
        });

        // --- ¡ACCIÓN REQUERIDA! ---
        // El error desapareció porque he comentado estas líneas.
        // Para que los sonidos funcionen, debes:
        // 1. Añadir tus archivos de sonido (ej: ui_click.mp3, note_hit.mp3) a la carpeta 'app/src/main/res/raw'.
        // 2. Descomentar las siguientes dos líneas.
        loadSound(context, SOUND_UI_CLICK, R.raw.ui_click);
        loadSound(context, SOUND_NOTE_HIT, R.raw.note_hit);
    }

    public static synchronized SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadSound(Context context, String name, int resId) {
        try {
            int soundId = soundPool.load(context, resId, 1);
            soundMap.put(name, soundId);
        } catch (Exception e) {
            // Esto pasará si no encuentras el archivo en res/raw
        }
    }

    public void playSound(String name) {
        if (isLoaded && soundMap.containsKey(name)) {
            soundPool.play(soundMap.get(name), 1, 1, 1, 0, 1.0f);
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        instance = null;
    }
}
