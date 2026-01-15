package com.example.skeletune.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.skeletune.constants.ApiConstants;
import com.example.skeletune.data.model.*;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.Resource;
import com.example.skeletune.data.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepository {
    private final ApiService apiService;

    public GameRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- CARGA DE JUEGO ---
    public LiveData<Resource<ChartMania>> getNivel(Integer idChart) {
        MutableLiveData<Resource<ChartMania>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getChartManiaById(idChart).enqueue(new Callback<ChartMania>() {
            @Override
            public void onResponse(Call<ChartMania> call, Response<ChartMania> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar el nivel"));
            }
            @Override
            public void onFailure(Call<ChartMania> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- RESULTADOS ---
    public LiveData<Resource<PartidaMania>> guardarPartida(PartidaMania partida) {
        MutableLiveData<Resource<PartidaMania>> result = new MutableLiveData<>();
        apiService.createPartidaMania(partida).enqueue(new Callback<PartidaMania>() {
            @Override
            public void onResponse(Call<PartidaMania> call, Response<PartidaMania> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("No se pudo guardar tu puntaje"));
            }
            @Override
            public void onFailure(Call<PartidaMania> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}