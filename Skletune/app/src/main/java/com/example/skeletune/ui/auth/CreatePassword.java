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

import com.example.skeletune.data.model.Usuario;
import com.example.skeletune.ui.common.viewmodels.AuthViewModel;
import com.example.skeletune.utils.RegistroHelper;
import com.example.skletune.R;

public class CreatePassword extends Fragment {

    private static final String TAG = "CreatePassword";
    private EditText editTextContrasena;
    private EditText editTextConfirmarContrasena;
    private Button botonSiguiente;
    private ProgressBar progressBar;

    // Vinculamos el ViewModel
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_password, container, false);

        // Inicializar vistas
        editTextContrasena = view.findViewById(R.id.editTextContrasena);
        editTextConfirmarContrasena = view.findViewById(R.id.editTextConfirmarContrasena);
        botonSiguiente = view.findViewById(R.id.botonenviarcontrasena);
        progressBar = view.findViewById(R.id.progressBar);

        // Inicializar ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        botonSiguiente.setOnClickListener(v -> {
            validarYRegistrar();
        });

        return view;
    }

    private void validarYRegistrar() {
        String contrasena = editTextContrasena.getText().toString().trim();
        String confirmar = editTextConfirmarContrasena.getText().toString().trim();

        // ... (Mantén tus validaciones de contraseña aquí) ...

        // 1. Recuperar los datos del Singleton RegistroHelper
        String nombreFinal = RegistroHelper.getInstance().getNombre();
        String correoFinal = RegistroHelper.getInstance().getCorreo();

        // 2. LOG IMPORTANTE: Revisa esto en el Logcat de Android Studio
        Log.d(TAG, "REGISTRO FINAL -> Nombre: " + nombreFinal + " | Correo: " + correoFinal);

        if (nombreFinal == null || nombreFinal.isEmpty()) {
            Toast.makeText(getContext(), "Error: El nombre se perdió en el camino", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Crear el objeto Usuario usando los SETTERS (más seguro que el constructor)
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombreFinal); // <--- ESTO ES LO QUE TE FALTA
        nuevoUsuario.setCorreo(correoFinal);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setIdRol(1); // Rol por defecto (Estudiante/Usuario)

        // 4. Llamar al ViewModel
        registrarEnServidor(nuevoUsuario);
    }

    private void registrarEnServidor(Usuario usuario) {
        authViewModel.registrarUsuario(usuario).observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    botonSiguiente.setEnabled(false);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    botonSiguiente.setEnabled(true);
                    Toast.makeText(getContext(), "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show();

                    RegistroHelper.getInstance().limpiar();
                    irAlLogin();
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    botonSiguiente.setEnabled(true);
                    Toast.makeText(getContext(), "Error: " + resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void irAlLogin() {
        login fragmentDestino = new login();
        getParentFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragmentDestino)
                .commit();
    }
}