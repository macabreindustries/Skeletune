package com.example.osu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";
    private CancionApiService apiService;

    private int idChart;
    private int score;
    private int maxCombo;
    private int perfects;
    private int greats;
    private int goods;
    private int misses;
    private BigDecimal accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // CORREGIDO: Usar la IP correcta del dispositivo, no la del emulador
        apiService = RetrofitClient.getClient("http://192.168.1.76:8080/skeletune/api/").create(CancionApiService.class);

        // Recuperar los datos
        idChart = getIntent().getIntExtra("ID_CHART", 0);
        score = getIntent().getIntExtra("SCORE", 0);
        maxCombo = getIntent().getIntExtra("MAX_COMBO", 0);
        perfects = getIntent().getIntExtra("PERFECTS", 0);
        greats = getIntent().getIntExtra("GREATS", 0);
        goods = getIntent().getIntExtra("GOODS", 0);
        misses = getIntent().getIntExtra("MISSES", 0);

        calculateAccuracy();
        displayResults();

        // CORRECCIÓN FINAL: El ID del botón se actualiza al nuevo que está en el layout
        Button saveButton = findViewById(R.id.saveAndExitButton);
        saveButton.setOnClickListener(v -> saveScore());
    }

    private void calculateAccuracy() {
        int totalNotes = perfects + greats + goods + misses;
        if (totalNotes > 0) {
            // Asignamos pesos: Perfect=1, Great=0.75, Good=0.5, Miss=0
            double totalPoints = (perfects * 1.0) + (greats * 0.75) + (goods * 0.5);
            double accValue = (totalPoints / totalNotes) * 100;
            accuracy = new BigDecimal(accValue).setScale(2, RoundingMode.HALF_UP);
        } else {
            accuracy = new BigDecimal("0.00");
        }
    }

    private void displayResults() {
        String grade = getGrade(accuracy, misses);

        ((TextView) findViewById(R.id.gradeTextView)).setText(grade);
        // Se ajusta el formato del score para que siempre tenga 7 dígitos
        ((TextView) findViewById(R.id.scoreTextView)).setText(String.format("%07d", score));
        ((TextView) findViewById(R.id.accuracyTextView)).setText(String.format("%.2f%%", accuracy.doubleValue()));
        
        // Se establece solo el valor numérico, la etiqueta ya está en el XML
        ((TextView) findViewById(R.id.maxComboTextView)).setText(String.valueOf(maxCombo));
        ((TextView) findViewById(R.id.perfectsTextView)).setText(String.valueOf(perfects));
        ((TextView) findViewById(R.id.greatsTextView)).setText(String.valueOf(greats));
        ((TextView) findViewById(R.id.goodsTextView)).setText(String.valueOf(goods));
        ((TextView) findViewById(R.id.missesTextView)).setText(String.valueOf(misses));
    }

    private String getGrade(BigDecimal accuracy, int misses) {
        if (accuracy.doubleValue() == 100.00) return "S";
        if (accuracy.doubleValue() >= 95.00 && misses == 0) return "A+";
        if (accuracy.doubleValue() >= 90.00) return "A";
        if (accuracy.doubleValue() >= 80.00) return "B";
        if (accuracy.doubleValue() >= 70.00) return "C";
        return "F";
    }

    private void saveScore() {
        PartidaMania partida = new PartidaMania();
        partida.setIdUsuario(1); // ID de usuario de prueba
        partida.setIdChartMania(idChart);
        partida.setPuntaje(score);
        partida.setComboMax(maxCombo);
        partida.setPerfects(perfects);
        partida.setGreats(greats);
        partida.setGoods(goods);
        partida.setMisses(misses);
        partida.setAccuracy(accuracy);

        apiService.savePartida(partida).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResultsActivity.this, "Puntuación Guardada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResultsActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ResultsActivity.this, "Fallo de red", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
