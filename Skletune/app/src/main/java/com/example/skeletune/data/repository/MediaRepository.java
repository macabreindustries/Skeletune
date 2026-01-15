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

public class MediaRepository {
    private final ApiService apiService;

    public MediaRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    public LiveData<Resource<Media>> registrarMedia(Media media) {
        MutableLiveData<Resource<Media>> result = new MutableLiveData<>();
        apiService.createMedia(media).enqueue(new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al registrar archivo"));
            }
            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}