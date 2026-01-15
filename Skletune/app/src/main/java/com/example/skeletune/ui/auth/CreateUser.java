package com.example.skeletune.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.skeletune.utils.RegistroHelper;
import com.example.skletune.R;

public class CreateUser extends Fragment {

    private static final String TAG = "CreateUser";
    private EditText editTextNombre;
    private Button botonSiguiente;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        // Inicializar componentes
        editTextNombre = view.findViewById(R.id.editTextNombre);
        botonSiguiente = view.findViewById(R.id.botonenviarusuario);

        botonSiguiente.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();

            // Validaciones básicas de UI
            if (nombre.isEmpty()) {
                Toast.makeText(getContext(), "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nombre.length() < 3) {
                Toast.makeText(getContext(), "El nombre debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar el nombre en el Helper (Persistencia temporal entre fragmentos)
            RegistroHelper.getInstance().setNombre(nombre);
            Log.d(TAG, "Nombre capturado: " + nombre);

            // Navegar al siguiente paso: Creación de contraseña
            irACreatePassword();
        });

        return view;
    }

    private void irACreatePassword() {
        CreatePassword fragmentDestino = new CreatePassword();

        // Es mejor usar el contenedor de tu MainActivity (R.id.fragment_container)
        // para que se mantenga dentro del diseño de la app
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentDestino)
                .addToBackStack(null) // Permite al usuario regresar si se equivocó de nombre
                .commit();
    }
}