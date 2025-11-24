package com.example.skletune;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InicioSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        Button botonContinuar = findViewById(R.id.botonenviarcorreo);

        botonContinuar.setOnClickListener(v -> {
            // Reemplazar toda la vista de la Activity por el fragmento CreateUser
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new CreateUser())
                    .addToBackStack(null)
                    .commit();
        });
    }
}