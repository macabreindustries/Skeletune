package com.example.skeletune.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.skeletune.data.model.Cancion;
import com.example.skeletune.data.model.Instrumento;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.Resource;
import com.example.skeletune.data.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRepository {
    private final ApiService apiService;

    public MusicRepository() {
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- NUEVO: Obtener una canción aleatoria para el Swipe ---
    public LiveData<Resource<Cancion>> obtenerCancionAleatoria() {
        MutableLiveData<Resource<Cancion>> result = new MutableLiveData<>();
        result.setValue(Resource.loading()); // Corregido: sin parámetros

        apiService.getCancionAleatoria().enqueue(new Callback<Cancion>() {
            @Override
            public void onResponse(Call<Cancion> call, Response<Cancion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error("No se pudo obtener una canción")); // Corregido: solo mensaje
                }
            }

            @Override
            public void onFailure(Call<Cancion> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage())); // Corregido: solo mensaje
            }
        });
        return result;
    }

    // --- NUEVO: Registrar Interacción (Like, View, Swipe) ---
    public LiveData<Resource<Void>> registrarInteraccion(int idCancion, String tipo) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();

        apiService.registrarInteraccion(idCancion, tipo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    result.setValue(Resource.success(null));
                } else {
                    result.setValue(Resource.error("Error al registrar " + tipo)); // Corregido
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage())); // Corregido
            }
        });
        return result;
    }

    // --- MÉTODOS ANTERIORES (Corregidos también) ---
    public LiveData<Resource<List<Cancion>>> buscarCanciones(String titulo, String artista, String dificultad) {
        MutableLiveData<Resource<List<Cancion>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading()); // Corregido
        apiService.getAllCanciones(titulo, artista, dificultad, null, null, null).enqueue(new Callback<List<Cancion>>() {
            @Override
            public void onResponse(Call<List<Cancion>> call, Response<List<Cancion>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al buscar canciones")); // Corregido
            }
            @Override
            public void onFailure(Call<List<Cancion>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage())); // Corregido
            }
        });
        return result;
    }

    public LiveData<Resource<List<Instrumento>>> getInstrumentos() {
        MutableLiveData<Resource<List<Instrumento>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading()); // Corregido para consistencia
        apiService.getAllInstrumentos().enqueue(new Callback<List<Instrumento>>() {
            @Override
            public void onResponse(Call<List<Instrumento>> call, Response<List<Instrumento>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar instrumentos")); // Corregido
            }
            @Override
            public void onFailure(Call<List<Instrumento>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage())); // Corregido
            }
        });
        return result;
    }
}