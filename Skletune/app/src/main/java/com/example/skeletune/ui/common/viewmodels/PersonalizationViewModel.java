package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Progreso;
import com.example.skeletune.data.model.UsuarioInstrumento;
import com.example.skeletune.data.repository.PersonalizationRepository;
import com.example.skeletune.data.network.Resource;

public class PersonalizationViewModel extends ViewModel {
    private final PersonalizationRepository personalizationRepository;

    private final MutableLiveData<Resource<Progreso>> _progresoResult = new MutableLiveData<>();
    public LiveData<Resource<Progreso>> getProgresoResult() { return _progresoResult; }

    public PersonalizationViewModel() {
        this.personalizationRepository = new PersonalizationRepository();
    }

    // Acción: Guardar que el usuario completó una lección
    public void guardarProgreso(Progreso progreso) {
        personalizationRepository.registrarProgreso(progreso).observeForever(resource -> {
            _progresoResult.setValue(resource);
        });
    }

    // Acción: Vincular un nuevo instrumento al perfil del usuario
    public LiveData<Resource<UsuarioInstrumento>> vincularInstrumento(UsuarioInstrumento ui) {
        return personalizationRepository.asignarInstrumento(ui);
    }
}