package com.example.skeletune.ui.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.skeletune.OSU.CancionApiService;
import com.example.skeletune.OSU.PartidaMania;
import com.example.skeletune.OSU.RetrofitClient;
import com.example.skeletune.OSU.SongSelectionActivity;

import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class gamesrecords extends Fragment {

    private SessionManager sessionManager;
    private CancionApiService apiService;

    private TextView tvTotalPartidas, tvPuntajeMaximo, tvPromedioAccuracy;

    public gamesrecords() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gamesrecords, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getContext());
        String API_URL = "http://10.0.2.2:8080/skeletune/api/";
        apiService = RetrofitClient.getClient(API_URL).create(CancionApiService.class);

        tvTotalPartidas = view.findViewById(R.id.tv_total_partidas);
        tvPuntajeMaximo = view.findViewById(R.id.tv_puntaje_maximo);
        tvPromedioAccuracy = view.findViewById(R.id.tv_promedio_accuracy);

        CardView cardGame1 = view.findViewById(R.id.card_game_1);
        cardGame1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SongSelectionActivity.class);
            startActivity(intent);
        });

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        int userId = sessionManager.getUserId();
        if (userId == -1) {
            return;
        }

        apiService.getPartidasPorUsuario(userId).enqueue(new Callback<List<PartidaMania>>() {
            @Override
            public void onResponse(Call<List<PartidaMania>> call, Response<List<PartidaMania>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    calcularYMostrarEstadisticas(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PartidaMania>> call, Throwable t) {
                // Manejar fallo de red si es necesario
            }
        });
    }

    private void calcularYMostrarEstadisticas(List<PartidaMania> partidas) {
        int totalPartidas = partidas.size();
        int puntajeMaximo = 0;
        BigDecimal sumaAccuracy = BigDecimal.ZERO;

        for (PartidaMania partida : partidas) {
            if (partida.getPuntaje() > puntajeMaximo) {
                puntajeMaximo = partida.getPuntaje();
            }
            sumaAccuracy = sumaAccuracy.add(partida.getAccuracy());
        }

        BigDecimal promedioAccuracy = BigDecimal.ZERO;
        if (totalPartidas > 0) {
            promedioAccuracy = sumaAccuracy.divide(new BigDecimal(totalPartidas), 2, RoundingMode.HALF_UP);
        }

        tvTotalPartidas.setText(String.valueOf(totalPartidas));
        tvPuntajeMaximo.setText(NumberFormat.getNumberInstance(Locale.US).format(puntajeMaximo));
        tvPromedioAccuracy.setText(String.format(Locale.US, "%.2f%%", promedioAccuracy));
    }
}
