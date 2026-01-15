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

public class PersonalizationRepository {
    private final ApiService apiService;

    public PersonalizationRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- PROGRESO ---
    public LiveData<Resource<Progreso>> registrarProgreso(Progreso progreso) {
        MutableLiveData<Resource<Progreso>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.createProgreso(progreso).enqueue(new Callback<Progreso>() {
            @Override
            public void onResponse(Call<Progreso> call, Response<Progreso> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("No se pudo guardar el progreso"));
            }
            @Override
            public void onFailure(Call<Progreso> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- INSTRUMENTOS DEL USUARIO ---
    public LiveData<Resource<UsuarioInstrumento>> asignarInstrumento(UsuarioInstrumento ui) {
        MutableLiveData<Resource<UsuarioInstrumento>> result = new MutableLiveData<>();
        apiService.createUsuarioInstrumento(ui).enqueue(new Callback<UsuarioInstrumento>() {
            @Override
            public void onResponse(Call<UsuarioInstrumento> call, Response<UsuarioInstrumento> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al asignar instrumento"));
            }
            @Override
            public void onFailure(Call<UsuarioInstrumento> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}