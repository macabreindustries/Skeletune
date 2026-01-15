package com.example.skeletune.ui.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.skeletune.utils.SessionManager; // Importación de nuestra utilidad de sesión
import com.example.skletune.R;

public class OptionsPlan extends Fragment {

    private String rolSeleccionado = "estudiante";
    private SessionManager sessionManager; // Declaración del gestor de sesión

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperar el rol que viene desde RolSelect
        if (getArguments() != null) {
            rolSeleccionado = getArguments().getString("rol", "estudiante");
        }

        // Inicializar el gestor de sesión
        sessionManager = new SessionManager(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_options_plan, container, false);

        Button botonBasic = view.findViewById(R.id.btnBasic);
        Button botonPremium = view.findViewById(R.id.btnPremium);

        // Listener optimizado para la arquitectura
        View.OnClickListener listener = v -> {

            String planSeleccionado = (v.getId() == R.id.btnBasic) ? "Basic" : "Premium";

            // 1. Persistir los datos finales en la sesión local
            // Nota: Aquí podrías extender tu SessionManager para guardar 'plan' y 'rol' si lo necesitas

            Toast.makeText(getContext(),
                    "Rol: " + rolSeleccionado + " | Plan: " + planSeleccionado,
                    Toast.LENGTH_SHORT).show();

            // 2. Iniciar la Activity principal de navegación
            Intent intent = new Intent(getActivity(), NavigationHostActivity.class);
            intent.putExtra("rol", rolSeleccionado);
            intent.putExtra("plan", planSeleccionado);

            // Limpiar el stack de actividades para que el usuario no pueda volver al registro
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            if (getActivity() != null) {
                getActivity().finish(); // Cierra el flujo de autenticación
            }
        };

        botonBasic.setOnClickListener(listener);
        botonPremium.setOnClickListener(listener);

        return view;
    }
}