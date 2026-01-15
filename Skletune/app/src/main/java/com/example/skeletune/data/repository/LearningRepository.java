package com.example.skeletune.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.skeletune.data.model.Leccion;
import com.example.skeletune.data.model.VideoEducativo;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.Resource;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.constants.ApiConstants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearningRepository {
    private final ApiService apiService;

    public LearningRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    public LiveData<Resource<List<VideoEducativo>>> getVideos(Integer idProfesor, String titulo) {
        MutableLiveData<Resource<List<VideoEducativo>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getAllVideoEducativos(idProfesor, titulo).enqueue(new Callback<List<VideoEducativo>>() {
            @Override
            public void onResponse(Call<List<VideoEducativo>> call, Response<List<VideoEducativo>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar videos"));
            }
            @Override
            public void onFailure(Call<List<VideoEducativo>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    public LiveData<Resource<List<Leccion>>> getLeccionesPorNivel(String nivel) {
        MutableLiveData<Resource<List<Leccion>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getLeccionesByNivel(nivel).enqueue(new Callback<List<Leccion>>() {
            @Override
            public void onResponse(Call<List<Leccion>> call, Response<List<Leccion>> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar lecciones"));
            }
            @Override
            public void onFailure(Call<List<Leccion>> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }
}