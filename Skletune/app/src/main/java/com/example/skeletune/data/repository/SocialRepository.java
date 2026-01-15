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

public class SocialRepository {
    private final ApiService apiService;

    public SocialRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- PUBLICACIONES (FEED) ---
    public LiveData<Resource<List<Publicacion>>> getFeed() {
        MutableLiveData<Resource<List<Publicacion>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getAllPublicaciones().enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar el muro"));
            }
            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- LIKES ---
    public LiveData<Resource<LikePublicacion>> darLike(Integer idUsuario, Integer idPublicacion) {
        MutableLiveData<Resource<LikePublicacion>> result = new MutableLiveData<>();
        apiService.likePublicacion(idUsuario, idPublicacion).enqueue(new Callback<LikePublicacion>() {
            @Override
            public void onResponse(Call<LikePublicacion> call, Response<LikePublicacion> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("No se pudo dar like"));
            }
            @Override
            public void onFailure(Call<LikePublicacion> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- SEGUIDORES ---
    public LiveData<Resource<Seguidor>> seguirUsuario(Integer idSeguidor, Integer idSeguido) {
        MutableLiveData<Resource<Seguidor>> result = new MutableLiveData<>();
        apiService.follow(idSeguidor, idSeguido).enqueue(new Callback<Seguidor>() {
            @Override
            public void onResponse(Call<Seguidor> call, Response<Seguidor> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al seguir usuario"));
            }
            @Override
            public void onFailure(Call<Seguidor> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}