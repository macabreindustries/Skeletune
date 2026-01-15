package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Leccion;
import com.example.skeletune.data.model.VideoEducativo;
import com.example.skeletune.data.repository.LearningRepository;
import com.example.skeletune.data.network.Resource;
import java.util.List;

public class LearningViewModel extends ViewModel {
    private final LearningRepository learningRepository;

    private final MutableLiveData<Resource<List<VideoEducativo>>> _videos = new MutableLiveData<>();
    public LiveData<Resource<List<VideoEducativo>>> getVideos() { return _videos; }

    private final MutableLiveData<Resource<List<Leccion>>> _lecciones = new MutableLiveData<>();
    public LiveData<Resource<List<Leccion>>> getLecciones() { return _lecciones; }

    public LearningViewModel() {
        this.learningRepository = new LearningRepository();
    }

    // Acción: Filtrar videos por título o profesor
    public void buscarVideos(Integer idProfesor, String titulo) {
        learningRepository.getVideos(idProfesor, titulo).observeForever(resource -> {
            _videos.setValue(resource);
        });
    }

    // Acción: Cargar lecciones según el nivel del alumno (Básico, Intermedio, etc.)
    public void cargarLeccionesPorNivel(String nivel) {
        learningRepository.getLeccionesPorNivel(nivel).observeForever(resource -> {
            _lecciones.setValue(resource);
        });
    }
}