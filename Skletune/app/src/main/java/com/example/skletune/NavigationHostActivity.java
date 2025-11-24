package com.example.skletune;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHostActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private String rolUsuario = "estudiante"; // Por defecto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_host);

        // Obtener el rol del usuario desde el Intent
        if (getIntent().hasExtra("rol")) {
            rolUsuario = getIntent().getStringExtra("rol");
        }

        // Obtener la referencia de la barra de navegación
        navView = findViewById(R.id.bottom_nav_view);

        // Cargar el Fragmento inicial según el rol
        if (savedInstanceState == null) {
            if (rolUsuario.equals("estudiante")) {
                loadFragment(new home());
            } else
                if (rolUsuario.equals("profesor")) {
                loadFragment(new Certifications());
            }
            navView.setSelectedItemId(R.id.nav_home); // Marca "Home" como seleccionado
        }

        // Establecer el Listener para manejar los clics en la barra
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Dependiendo del rol, carga el fragmento correspondiente
                if (rolUsuario.equals("estudiante")) {
                    selectedFragment = new home();
                } else {
                    selectedFragment = new homeprofessor();
                }
            } else
                if (itemId == R.id.nav_music) {
                selectedFragment = new music();
            } else
                if (itemId == R.id.nav_placeholder) {
                selectedFragment = new Nepublication();
            } else
                if (itemId == R.id.nav_notifications) {
                return false;
            } else
                if (itemId == R.id.nav_profile) {
                return false;
            }

            // Cargar el Fragmento seleccionado
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplaza el contenido del fragment_container
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Si estamos en el fragmento home o homeprofessor, salir de la app
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof home || currentFragment instanceof homeprofessor) {
            super.onBackPressed();
        } else {
            // Si no, volver a home
            navView.setSelectedItemId(R.id.nav_home);
        }
    }
}