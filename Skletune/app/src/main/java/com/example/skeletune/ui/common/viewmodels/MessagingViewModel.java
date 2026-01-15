package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Mensaje;
import com.example.skeletune.data.model.Notificacion;
import com.example.skeletune.data.repository.MessagingRepository;
import com.example.skeletune.data.network.Resource;
import java.util.List;

public class MessagingViewModel extends ViewModel {
    private final MessagingRepository messagingRepository;

    private final MutableLiveData<Resource<List<Mensaje>>> _chatHistory = new MutableLiveData<>();
    public LiveData<Resource<List<Mensaje>>> getChatHistory() { return _chatHistory; }

    private final MutableLiveData<Resource<List<Notificacion>>> _notificaciones = new MutableLiveData<>();
    public LiveData<Resource<List<Notificacion>>> getNotificaciones() { return _notificaciones; }

    public MessagingViewModel() {
        this.messagingRepository = new MessagingRepository();
    }

    // Acción: Cargar conversación entre dos usuarios
    public void cargarChat(Integer miId, Integer suId) {
        messagingRepository.getConversacion(miId, suId).observeForever(resource -> {
            _chatHistory.setValue(resource);
        });
    }

    // Acción: Enviar un mensaje nuevo
    public void enviarMensaje(Mensaje msg) {
        messagingRepository.enviarMensaje(msg).observeForever(resource -> {
            // Aquí podrías recargar el chat o añadir el mensaje a la lista local
        });
    }

    // Acción: Cargar notificaciones del usuario
    public void cargarNotificaciones(Integer idUsuario) {
        messagingRepository.getNotificaciones(idUsuario).observeForever(resource -> {
            _notificaciones.setValue(resource);
        });
    }
}