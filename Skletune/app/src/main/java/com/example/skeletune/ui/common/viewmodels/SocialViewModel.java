package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Publicacion;
import com.example.skeletune.data.model.LikePublicacion;
import com.example.skeletune.data.model.Seguidor;
import com.example.skeletune.data.repository.SocialRepository;
import com.example.skeletune.data.network.Resource;
import java.util.List;

public class SocialViewModel extends ViewModel {
    private final SocialRepository socialRepository;

    private final MutableLiveData<Resource<List<Publicacion>>> _feed = new MutableLiveData<>();
    public LiveData<Resource<List<Publicacion>>> getFeed() { return _feed; }

    private final MutableLiveData<Resource<LikePublicacion>> _likeResult = new MutableLiveData<>();
    public LiveData<Resource<LikePublicacion>> getLikeResult() { return _likeResult; }

    public SocialViewModel() {
        this.socialRepository = new SocialRepository();
    }

    // Acción: Cargar todas las publicaciones del muro
    public void cargarMuro() {
        socialRepository.getFeed().observeForever(resource -> {
            _feed.setValue(resource);
        });
    }

    // Acción: Dar like a una publicación
    public void reaccionarAPublicacion(Integer idUsuario, Integer idPublicacion) {
        socialRepository.darLike(idUsuario, idPublicacion).observeForever(resource -> {
            _likeResult.setValue(resource);
        });
    }

    // Acción: Seguir a un nuevo músico
    public LiveData<Resource<Seguidor>> seguirMúsico(Integer miId, Integer idDestino) {
        return socialRepository.seguirUsuario(miId, idDestino);
    }
}