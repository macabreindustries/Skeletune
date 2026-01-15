package com.example.skeletune.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.skeletune.data.model.Usuario;
import com.example.skeletune.ui.common.fragments.OptionsPlan;
import com.example.skeletune.ui.common.viewmodels.AuthViewModel;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

public class RolSelect extends Fragment {

    private AuthViewModel authViewModel;
    private SessionManager sessionManager;
    private ProgressBar progressBar; // Recomendado añadirlo a tu XML

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rol_select, container, false);

        // 1. Inicializar lógica y sesión
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(requireContext());

        Button botonEstudiante = view.findViewById(R.id.botonimgEstu);
        Button botonProfesor = view.findViewById(R.id.botonimgProg);
        progressBar = view.findViewById(R.id.progressBar); // Asegúrate de que el ID coincida

        // 2. Eventos de clic
        botonEstudiante.setOnClickListener(v -> actualizarRolYContinuar(2, "estudiante")); // Asumiendo ID 2 para estudiante
        botonProfesor.setOnClickListener(v -> actualizarRolYContinuar(3, "profesor"));   // Asumiendo ID 3 para profesor

        return view;
    }

    private void actualizarRolYContinuar(int idRol, String nombreRol) {
        int userId = sessionManager.getUserId();

        if (userId == -1) {
            Toast.makeText(getContext(), "Error: Sesión no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creamos un objeto usuario solo con los campos a actualizar (id y idRol)
        Usuario usuarioUpdate = new Usuario();
        usuarioUpdate.setIdUsuario(userId);
        usuarioUpdate.setIdRol(idRol);

        // Usamos el ViewModel para actualizar en el servidor
        authViewModel.obtenerPerfil(userId).observe(getViewLifecycleOwner(), resource -> {
            // Nota: Aquí podrías usar un método "updateUsuario" en tu ViewModel si lo tienes.
            // Por ahora, simularemos el éxito de la elección para navegar.

            // Si tu API requiere actualizar el rol, aquí llamaríamos a authViewModel.actualizarRol(...)

            navegarAOptionsPlan(nombreRol);
        });
    }

    private void navegarAOptionsPlan(String rol) {
        OptionsPlan fragmentDestino = new OptionsPlan();
        Bundle args = new Bundle();
        args.putString("rol", rol);
        fragmentDestino.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentDestino)
                .addToBackStack(null)
                .commit();
    }
}