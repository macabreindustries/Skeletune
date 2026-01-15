package com.example.skeletune.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.skeletune.ui.common.viewmodels.AuthViewModel;
import com.example.skeletune.utils.SessionManager;
import com.example.skletune.R;

public class login extends Fragment {

    private EditText editTextUsuario;
    private EditText editTextContrasena;
    private Button botonLogin;
    private ProgressBar progressBar;

    // Declaramos el ViewModel y SessionManager
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Inicializar UI
        editTextUsuario = view.findViewById(R.id.editTextUsuario);
        editTextContrasena = view.findViewById(R.id.editTextContrasena);
        botonLogin = view.findViewById(R.id.botonarol);
        progressBar = view.findViewById(R.id.progressBar);

        // Inicializar Lógica
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(getContext());

        botonLogin.setOnClickListener(v -> {
            String correo = editTextUsuario.getText().toString().trim();
            String pass = editTextContrasena.getText().toString().trim();

            if (!correo.isEmpty() && !pass.isEmpty()) {
                iniciarSesion(correo, pass);
            } else {
                Toast.makeText(getContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void iniciarSesion(String correo, String pass) {
        // ❌ CAMBIA ESTO:
        // authViewModel.loginOrRegister(correo, pass);

        // ✅ POR ESTO (Usar el método de login real):
        authViewModel.login(correo, pass).observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    botonLogin.setEnabled(false);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    botonLogin.setEnabled(true);
                    if (resource.data != null) {
                        // Guardar sesión con los datos que VIENEN DEL SERVIDOR
                        sessionManager.saveUser(resource.data.getIdUsuario(), resource.data.getNombre());
                        irARolSelect();
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    botonLogin.setEnabled(true);
                    Toast.makeText(getContext(), "Credenciales incorrectas: " + resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void irARolSelect() {
        RolSelect fragmentDestino = new RolSelect();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentDestino) // Usa el ID de tu contenedor real
                .commit();
    }
}