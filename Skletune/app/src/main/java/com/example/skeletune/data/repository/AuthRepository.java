package com.example.skeletune.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.skeletune.data.model.Usuario;
import com.example.skeletune.data.model.ValidacionRol;
import com.example.skeletune.data.model.EstadisticaUsuario;
import com.example.skeletune.data.network.ApiService;
import com.example.skeletune.data.network.Resource;
import com.example.skeletune.data.network.RetrofitClient;
import com.example.skeletune.constants.ApiConstants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final ApiService apiService;

    public AuthRepository() {
        // Accedemos a la instancia única y pedimos el servicio directamente
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- USUARIOS ---
    public LiveData<Resource<Usuario>> registrarUsuario(Usuario usuario) {
        MutableLiveData<Resource<Usuario>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.createUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al registrar: " + response.code()));
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    public LiveData<Resource<Usuario>> getUsuario(int id) {
        MutableLiveData<Resource<Usuario>> result = new MutableLiveData<>();
        apiService.getUsuarioById(id).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("No se encontró el usuario"));
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // --- ESTADÍSTICAS ---
    public LiveData<Resource<EstadisticaUsuario>> getEstadisticas(int idUsuario) {
        MutableLiveData<Resource<EstadisticaUsuario>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        apiService.getEstadisticasByUsuarioId(idUsuario).enqueue(new Callback<EstadisticaUsuario>() {
            @Override
            public void onResponse(Call<EstadisticaUsuario> call, Response<EstadisticaUsuario> response) {
                if (response.isSuccessful()) result.setValue(Resource.success(response.body()));
                else result.setValue(Resource.error("Error al cargar estadísticas"));
            }
            @Override
            public void onFailure(Call<EstadisticaUsuario> call, Throwable t) {
                result.setValue(Resource.error(t.getMessage()));
            }
        });
        return result;
    }

    // En AuthRepository.java

    public LiveData<Resource<Usuario>> login(String correo, String contrasena) {
        MutableLiveData<Resource<Usuario>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        apiService.login(correo, contrasena).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error("Correo o contraseña incorrectos"));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                result.setValue(Resource.error("Fallo de red: " + t.getMessage()));
            }
        });
        return result;
    }
}