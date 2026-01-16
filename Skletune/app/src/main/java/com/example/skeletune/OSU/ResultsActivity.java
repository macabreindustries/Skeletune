package com.example.skeletune.OSU;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skletune.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private static final String API_URL = "http://10.0.2.2:8080/skeletune/api/";
    private CancionApiService apiService;

    private int idChart;
    private int score;
    private int maxCombo;
    private int perfects;
    private int greats;
    private int goods;
    private int misses;
    private int userId; // Variable para almacenar el ID del usuario
    private BigDecimal accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Se corrige la URL del servidor y se instancia el servicio de la API
        apiService = RetrofitClient.getClient(API_URL).create(CancionApiService.class);

        // Recuperar los datos del Intent, incluyendo el ID del usuario
        idChart = getIntent().getIntExtra("ID_CHART", 0);
        score = getIntent().getIntExtra("SCORE", 0);
        maxCombo = getIntent().getIntExtra("MAX_COMBO", 0);
        perfects = getIntent().getIntExtra("PERFECTS", 0);
        greats = getIntent().getIntExtra("GREATS", 0);
        goods = getIntent().getIntExtra("GOODS", 0);
        misses = getIntent().getIntExtra("MISSES", 0);
        userId = getIntent().getIntExtra("USER_ID", -1); // Se obtiene el ID del usuario

        calculateAccuracy();
        displayResults();

        Button saveButton = findViewById(R.id.saveAndExitButton);
        saveButton.setOnClickListener(v -> saveScore());
    }

    private void calculateAccuracy() {
        int totalNotes = perfects + greats + goods + misses;
        if (totalNotes > 0) {
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
        ((TextView) findViewById(R.id.scoreTextView)).setText(String.format("%07d", score));
        ((TextView) findViewById(R.id.accuracyTextView)).setText(String.format("%.2f%%", accuracy.doubleValue()));
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
        if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo identificar al usuario.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PartidaMania partida = new PartidaMania();
        partida.setIdUsuario(userId); // Se usa el ID del usuario correcto
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