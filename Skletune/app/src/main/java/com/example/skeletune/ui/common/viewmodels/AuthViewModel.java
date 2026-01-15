package com.example.skeletune.ui.common.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.skeletune.data.model.Usuario;
import com.example.skeletune.data.repository.AuthRepository;
import com.example.skeletune.data.network.Resource;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    // LiveData que contiene el resultado de la última operación de autenticación
    private final MutableLiveData<Resource<Usuario>> _authResult = new MutableLiveData<>();
    public LiveData<Resource<Usuario>> getAuthResult() { return _authResult; }

    public AuthViewModel() {
        this.authRepository = new AuthRepository();
    }

    // --- ACCIÓN 1: LOGIN (Inicio de Sesión) ---
    // Esta versión actualiza el _authResult interno del ViewModel
    public void realizarLogin(String correo, String contrasena) {
        authRepository.login(correo, contrasena).observeForever(resource -> {
            _authResult.setValue(resource);
        });
    }

    // Esta versión (la que me pediste) retorna el LiveData directamente al Fragmento
    public LiveData<Resource<Usuario>> login(String correo, String contrasena) {
        return authRepository.login(correo, contrasena);
    }

    // --- ACCIÓN 2: REGISTRO (Crear Cuenta) ---
    public LiveData<Resource<Usuario>> registrarUsuario(Usuario usuario) {
        return authRepository.registrarUsuario(usuario);
    }

    // Método híbrido que tenías originalmente (opcional mantenerlo)
    public void loginOrRegister(String correo, String contrasena) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);

        authRepository.registrarUsuario(usuario).observeForever(resource -> {
            _authResult.setValue(resource);
        });
    }

    // --- ACCIÓN 3: PERFIL ---
    public LiveData<Resource<Usuario>> obtenerPerfil(int userId) {
        return authRepository.getUsuario(userId);
    }
}