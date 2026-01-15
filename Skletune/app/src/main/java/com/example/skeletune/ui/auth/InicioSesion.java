package com.example.skeletune.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skeletune.utils.RegistroHelper;
import com.example.skletune.R;

public class InicioSesion extends AppCompatActivity {

    private static final String TAG = "InicioSesion";
    private EditText editTextCorreo;
    private Button botonContinuar;
    private TextView textViewIrALogin; // Variable para el nuevo acceso al Login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // 1. Limpiar cualquier dato residual del Singleton para evitar basura de registros previos
        RegistroHelper.getInstance().limpiar();

        // 2. Inicializar componentes de la UI
        editTextCorreo = findViewById(R.id.editTextCorreo);
        botonContinuar = findViewById(R.id.botonenviarcorreo);
        textViewIrALogin = findViewById(R.id.textViewIrALogin); // El ID que agregamos al XML

        // 3. Lógica para el flujo de REGISTRO (Botón Continuar)
        botonContinuar.setOnClickListener(v -> {
            String correo = editTextCorreo.getText().toString().trim();

            if (validarCorreo(correo)) {
                // Guardamos el correo en el helper temporalmente
                RegistroHelper.getInstance().setCorreo(correo);
                Log.d(TAG, "Correo capturado para registro: " + correo);

                // Preparamos la pantalla y navegamos al paso 1 del registro
                ocultarContenidoInicial();
                irACreateUser();
            }
        });

        // 4. Lógica para el flujo de LOGIN (Texto "¿Ya tienes cuenta?")
        textViewIrALogin.setOnClickListener(v -> {
            Log.d(TAG, "Navegando directamente al Login fragment");
            ocultarContenidoInicial();
            irALogin();
        });
    }

    /**
     * Valida que el correo no esté vacío y tenga un formato válido
     */
    private boolean validarCorreo(String correo) {
        if (correo.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu correo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Oculta el logo y el formulario inicial para que el fragmento sea visible
     */
    private void ocultarContenidoInicial() {
        View contenidoInicial = findViewById(R.id.layout_contenido_inicial);
        if (contenidoInicial != null) {
            contenidoInicial.setVisibility(View.GONE);
        }
    }

    /**
     * Navega al Fragmento de Creación de Usuario (Registro)
     */
    private void irACreateUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, new CreateUser())
                .addToBackStack(null) // Permite regresar al inicio con el botón "Atrás"
                .commit();
    }

    /**
     * Navega directamente al Fragmento de Login
     */
    private void irALogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, new login())
                .addToBackStack(null)
                .commit();
    }
}