package com.example.skeletune.ui.common.fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skeletune.ui.student.home;
import com.example.skeletune.ui.teacher.Certifications;
import com.example.skeletune.ui.teacher.homeprofessor;
import com.example.skeletune.utils.SessionManager; // Importamos el gestor de sesión
import com.example.skletune.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHostActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private SessionManager sessionManager;
    private String rolUsuario = "estudiante";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_host);

        // 1. Inicializar SessionManager para no depender solo del Intent
        sessionManager = new SessionManager(this);

        // Intentamos obtener el rol del Intent, si no, lo buscamos en la sesión guardada
        if (getIntent().hasExtra("rol")) {
            rolUsuario = getIntent().getStringExtra("rol");
        } else {
            // Aquí podrías implementar lógica en SessionManager para guardar el rol
            // Por ahora mantenemos la lógica de flujo
        }

        navView = findViewById(R.id.bottom_nav_view);

        // 2. Cargar Fragmento Inicial
        if (savedInstanceState == null) {
            setupInitialFragment();
        }

        // 3. Listener de Navegación optimizado con switch
        setupNavigationListener();
    }

    private void setupInitialFragment() {
        if (rolUsuario.equals("estudiante")) {
            loadFragment(new home());
        } else if (rolUsuario.equals("profesor")) {
            loadFragment(new Certifications());
        }
        navView.setSelectedItemId(R.id.nav_home);
    }

    private void setupNavigationListener() {
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            // Usar switch-case es más limpio para la arquitectura
            if (itemId == R.id.nav_home) {
                selectedFragment = rolUsuario.equals("estudiante") ? new home() : new homeprofessor();
            } else if (itemId == R.id.nav_music) {
                selectedFragment = new music();
            } else if (itemId == R.id.nav_placeholder) {
                selectedFragment = new Nepublication();
            } else if (itemId == R.id.nav_notifications) {
                // Aquí podrías cargar un NotificationsFragment en el futuro
                return false;
            } else if (itemId == R.id.nav_profile) {
                // Aquí podrías cargar un ProfileFragment
                selectedFragment = new profile();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Transición suave
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Si no estamos en el Home, el botón atrás nos regresa al Home antes de salir
        if (currentFragment instanceof home || currentFragment instanceof homeprofessor || currentFragment instanceof Certifications) {
            super.onBackPressed();
        } else {
            navView.setSelectedItemId(R.id.nav_home);
        }
    }
}