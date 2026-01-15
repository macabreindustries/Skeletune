package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Cancion;
import com.example.skeletune.data.repository.MusicRepository;
import com.example.skeletune.data.network.Resource;

public class MusicViewModel extends ViewModel {
    private final MusicRepository musicRepository;

    // LiveData para la canción que se muestra actualmente en el Swipe
    private final MutableLiveData<Resource<Cancion>> _cancionActual = new MutableLiveData<>();
    public LiveData<Resource<Cancion>> getCancionActual() { return _cancionActual; }

    // LiveData para saber si la interacción (like/swipe) fue exitosa
    private final MutableLiveData<Resource<Void>> _interaccionStatus = new MutableLiveData<>();
    public LiveData<Resource<Void>> getInteraccionStatus() { return _interaccionStatus; }

    public MusicViewModel() {
        this.musicRepository = new MusicRepository();
    }

    // 1. Cargar una canción aleatoria al iniciar o saltar
    public void cargarCancionAleatoria() {
        _cancionActual.setValue(Resource.loading());
        musicRepository.obtenerCancionAleatoria().observeForever(resource -> {
            _cancionActual.setValue(resource);
        });
    }

    // 2. Registrar el "Like" (Match) y cargar la siguiente
    // 2. Registrar el "Like" (Match) - SOLO REGISTRA, NO SALTA
    public void darLike(int idCancion) {
        musicRepository.registrarInteraccion(idCancion, "like").observeForever(resource -> {
            // Quitamos el cargarCancionAleatoria() de aquí para que el usuario
            // pueda seguir escuchando la canción después de dar like.
            _interaccionStatus.setValue(resource);
        });
    }

    // 3. Registrar el "Swipe" (Rechazo) y cargar la siguiente
    public void rechazarCancion(int idCancion) {
        musicRepository.registrarInteraccion(idCancion, "swipe").observeForever(resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                cargarCancionAleatoria();
            }
        });
    }

    // 4. Registrar una "Vista" (cuando la canción empieza a sonar)
    public void registrarVista(int idCancion) {
        musicRepository.registrarInteraccion(idCancion, "view").observeForever(resource -> {
            // Solo actualizamos estadísticas si es necesario
        });
    }
}