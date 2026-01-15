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

public class MessagingRepository {
    private final ApiService apiService;

    public MessagingRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- CHAT ---
    public LiveData<Resource<List<Mensaje>>> getConversacion(Integer id1, Integer id2) {
        MutableLiveData<Resource<List<Mensaje>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getConversation(id1, id2).enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("No se pudo cargar el chat"));
            }
            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    public LiveData<Resource<Mensaje>> enviarMensaje(Mensaje mensaje) {
        MutableLiveData<Resource<Mensaje>> result = new MutableLiveData<>();
        apiService.sendMessage(mensaje).enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al enviar mensaje"));
            }
            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- NOTIFICACIONES ---
    public LiveData<Resource<List<Notificacion>>> getNotificaciones(Integer idUsuario) {
        MutableLiveData<Resource<List<Notificacion>>> result = new MutableLiveData<>();
        apiService.getNotificacionesByUsuario(idUsuario).enqueue(new Callback<List<Notificacion>>() {
            @Override
            public void onResponse(Call<List<Notificacion>> call, Response<List<Notificacion>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar notificaciones"));
            }
            @Override
            public void onFailure(Call<List<Notificacion>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}